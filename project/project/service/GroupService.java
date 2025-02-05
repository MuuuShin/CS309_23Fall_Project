package cse.ooad.project.service;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.utils.RoomStatus;
import jakarta.transaction.Transactional;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TimelineService timelineService;


    final Long STARLIMITE = 6L;


    // 仅用于测试缓存是否成功运行
    @Transactional
    public void test(){
        for(int i=0;i<10;i++){
            Group group1 = groupRepository.getGroupByGroupId(1L);
            System.out.println(group1);
        }

    }


    /**
     * group1合并进group2，阶段三的时候使用
     */
    @Transactional
    public Boolean combineGroups(Long id1, Long id2) {
        Group group1 = groupRepository.getGroupByGroupId(id1);
        Group group2 = groupRepository.getGroupByGroupId(id2);
        if (group1.getMemberList().size() + group2.getMemberList().size() <= 4) {
            group2.getMemberList().forEach(t -> {
                t.setGroupId(group1.getGroupId());
                studentRepository.save(t);
            });
            group2.setMemberList(null);
            group2 = groupRepository.save(group2);
            groupRepository.deleteByGroupId(group2.getGroupId());
            groupRepository.save(group1);
            return true;
        }
        return false;
    }

    /**
     * group将room添加入star中
     *
     * @param groupId 传入的队伍id
     * @param roomId  传入的房间id
     * @return star数量没超就返回true，否则返回false
     */
    @Transactional
    public boolean starRoom(Long groupId, Long roomId) {
        //已经存在
        if(groupRepository.getGroupByGroupId(groupId).getRoomStarList().contains(roomRepository.getRoomsByRoomId(roomId))){
            return false;
        }
        Group group = groupRepository.getGroupByGroupId(groupId);
        Room room = roomRepository.getRoomsByRoomId(roomId);
        int stage = timelineService.getStage(
            group.getMemberList().get(0).getType());
        if (stage != 1) {
            return false;
        }
        if (group.getRoomStarList().size() < STARLIMITE) {
            group.getRoomStarList().add(room);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    /**
     * group将room从star中移除
     *
     * @param groupId 传入的队伍id
     * @param roomId  传入的房间id
     * @return star中有这个房间且移除成功就返回true，否则返回false
     */
    @Transactional
    public boolean unstarRoom(Long groupId, Long roomId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        Room room = roomRepository.getRoomsByRoomId(roomId);
        int stage = timelineService.getStage(
                group.getMemberList().get(0).getType());
        if (stage != 1) {
            return false;
        }
        if (group.getRoomStarList().contains(room)) {
            group.getRoomStarList().remove(room);
            groupRepository.save(group);
            return true;
        }
        return false;
    }


    /**
     * @return 结合各个阶段判断选房是否成功
     */
    @Transactional
    public boolean chooseRoom(Long groupId, Long roomId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        Room room = roomRepository.getRoomsByRoomId(roomId);
      if (group == null|| room == null) {
          log.info("group or room is null");
            return false;
        }
        Student lead = studentRepository.getStudentByStudentId(group.getLeader());


        int stage = timelineService.getStage(lead.getType());
        //判断房间类型对不对
        if ((room.getType() - 1) / 4 + 1 != lead.getType()) {
            log.info("room type is not right");
            return false;
        }
        int roomCapacity = room.getType() % 4 == 0 ? 4 : room.getType() % 4;
        if (stage == 2) {
            //如果选房的人没有star
            if (!group.getRoomStarList().contains(room)) {
                log.info("group has no star");
                return false;
            }
            //房间已经被选了
            if (room.getStatus() != RoomStatus.UNSELECTED.statusCode) {
                log.info("room has been selected");
                return false;
            }

            //如果选房的人数不对
            if (group.getMemberList().size() != roomCapacity) {
                log.info("group member number is not right");
                return false;
            }
            //选房
            group.setRoomId(roomId);
            group.setRoom(room);
            room.setGroup(group);
            room.setStatus(RoomStatus.SELECTED.statusCode);
            roomRepository.save(room);
            groupRepository.save(group);
            log.info("choose room success");

        }
        if (stage == 3) {
            Group roomMaster = groupRepository.getGroupByRoomId(room.getRoomId());
            //看有人没人
            if (roomMaster == null) {
                if (group.getMemberList().size() > roomCapacity) {
                    log.info("group member number is not right");
                    return false;
                }
                group.setRoomId(roomId);
                group.setRoom(room);
                room.setGroup(group);
                room.setStatus(RoomStatus.SELECTED.statusCode);
                roomRepository.save(room);
                groupRepository.save(group);
                log.info("choose room success");
                return true;

            } else {
                if (roomMaster.getMemberList().size() + group.getMemberList().size()
                    > roomCapacity) {
                    log.info("group member number is not right");
                    return false;
                }
                log.info("combine groups");
                return combineGroups(roomMaster.getGroupId(), group.getGroupId());
            }


        }
        return false;
    }

    /**
     *
     * @param id 传入的队伍id
     * @return 返回队伍的成员列表
     */

    @Transactional
    public List<Student> getMemberList(Long id) {
        Group group = groupRepository.getGroupByGroupId(id);

        List<Student> students = group.getMemberList();
        Hibernate.initialize(students);
        return students;
    }

    /**

     * @return 返回所有队伍
     */

    public List<Group> getGroupsList() {
        return groupRepository.findAll();
    }



    /**
     * 修改队伍的leader
     * @param groupId 传入的队伍id
     * @param studentId 传入的学生id
     * @return 返回是否成功
     */
    @Transactional
    public Boolean changeLeader(Long groupId, Long studentId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        Student student = studentRepository.getStudentByStudentId(studentId);
        if (group.getMemberList().contains(student)) {
            group.setLeader(studentId);
            groupRepository.save(group);
            return true;
        }
        return false;
    }


    /**
     * 判断是否队伍领导
     * @param groupId 传入的队伍id
     * @param studentId 传入的学生id
     * @return 返回是否这个队伍的leader
     */
    public Boolean isLeader(Long groupId, Long studentId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        return group.getLeader().equals(studentId);
    }

    /**
     * group将room从star中移除
     *
     * @param groupId 传入的队伍id
     * @param roomId  传入的房间id
     * @return star中有这个房间且移除成功就返回true，否则返回false
     */
    @Transactional
    public boolean unStarRoom(Long groupId, Long roomId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        Room room = roomRepository.getRoomsByRoomId(roomId);
        int stage = timelineService.getStage(
            group.getMemberList().get(0).getType());
        if (stage != 1) {
            return false;
        }
        if (group.getRoomStarList().contains(room)) {
            group.getRoomStarList().remove(room);
            groupRepository.save(group);
            return true;
        }
        return false;
    }


    /**
     *
     * 获取队伍的star列表
     * @param id 传入的队伍id
     * @return 返回队伍的star列表
     */

    @Transactional
    public List<Room> getStarList(Long id) {
        Group group = groupRepository.getGroupByGroupId(id);
        List<Room> rooms = group.getRoomStarList();
        Hibernate.initialize(rooms);
        return rooms;
    }

    public boolean updateGroupIntro(Long id, String intro){
        Group group = groupRepository.getGroupByGroupId(id);
        if(group == null){
            return false;
        }
        group.setIntro(intro);
        groupRepository.save(group);
        return true;
    }


}
