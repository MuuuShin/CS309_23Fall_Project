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

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
            //group1.getMemberList().addAll(group2.getMemberList());
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
     * @param roomId 传入的房间id
     * @return star数量没超就返回true，否则返回false
     */
    @Transactional
    public boolean starRoom(Long groupId, Long roomId) {
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
     * @return 结合各个阶段判断选房是否成功
     */
    @Transactional
    public boolean chooseRoom(Long groupId, Long roomId) {
        Group group = groupRepository.getGroupByGroupId(groupId);
        Room room = roomRepository.getRoomsByRoomId(roomId);
        Student lead = studentRepository.getStudentByStudentId(group.getLeader());

        int stage = timelineService.getStage(lead.getType());
        //判断房间类型对不对
        if ((room.getType() - 1) / 4 + 1 != lead.getType()) {
            System.out.println(1);
            return false;
        }
        int roomCapacity = room.getType() % 4 == 0 ? 4 : room.getType() % 4;
        if (stage == 2) {
            //如果选房的人没有star
            if (!group.getRoomStarList().contains(room)) {
                System.out.println(2);
                return false;
            }
            //房间已经被选了
            if (room.getStatus() != RoomStatus.UNSELECTED.statusCode) {
                System.out.println(3);
                return false;
            }

            //如果选房的人数不对
            //todo
            if (group.getMemberList().size() != roomCapacity) {
                return false;
            }
            //选房
            group.setRoomId(roomId);
            group.setRoom(room);
            room.setGroup(group);
            room.setStatus(RoomStatus.SELECTED.statusCode);
            roomRepository.save(room);
            groupRepository.save(group);

        }
        if (stage == 3) {
            Group roomMaster = groupRepository.getGroupByRoomId(room.getRoomId());
            //看有人没人
            if (roomMaster == null) {
                if (group.getMemberList().size() > roomCapacity) {
                    return false;
                }
                group.setRoomId(roomId);
                group.setRoom(room);
                room.setGroup(group);
                room.setStatus(RoomStatus.SELECTED.statusCode);
                roomRepository.save(room);
                groupRepository.save(group);
                return true;

            } else {
                //todo 将两个队伍人数进行相加判断能不能选，能选就执行合并队伍操作
                if (roomMaster.getMemberList().size() + group.getMemberList().size()
                    > roomCapacity) {
                    return false;
                }
                return combineGroups(roomMaster.getGroupId(), group.getGroupId());
            }


        }
        System.out.println(10);
        return false;
    }

    @Transactional
    public List<Student> getMemberList(Long id) {
        //todo
        //根据学生id获取队伍
        Group group = groupRepository.getGroupByGroupId(id);
        return group.getMemberList();
    }

    public List<Group> getGroupsList() {
        return groupRepository.findAll();
    }

    /**
     * 获取star列表
     */

    @Transactional
    public List<Room> getStarList(Long id) {
        Group group = groupRepository.getGroupByGroupId(id);
        List<Room> rooms = group.getRoomStarList();
        Hibernate.initialize(rooms);
        return rooms;
    }


}
