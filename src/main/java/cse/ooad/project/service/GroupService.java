package cse.ooad.project.service;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
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
    public void combineGroups(Group group1, Group group2) {

    }

    /**
     * group将room添加入star中
     *
     * @param group
     * @param room
     * @return star数量没超就返回true，否则返回false
     */
    public boolean starRoom(Group group, Room room) {
        int stage = timelineService.getStage(
            studentRepository.getReferenceById(group.getMember1Id()).getType());
        if (stage != 1 && stage != 2){
            return false;
        }
        if (group.getRoomStarList().size() < STARLIMITE) {
            group.getRoomStarList().add(room);
            return true;
        }
        return false;
    }


    /**
     * @return 结合各个阶段判断选房是否成功
     */
    public boolean chooseRoom(Group group, Room room) {
        int stage = timelineService.getStage(
            studentRepository.getReferenceById(group.getMember1Id()).getType());
        if (stage == 2){
            //如果选房的人没有star
            if (!group.getRoomStarList().contains(room)){
                return false;
            }

            //如果选房的人数不对
            //todo


        }
        if (stage == 3){
            Group roomMaster = groupRepository.getGroupByRoomId(room.getRoomId());
            //看有人没人
            if (roomMaster == null){

            }else {
                //todo 将两个队伍人数进行相加判断能不能选，能选就执行合并队伍操作
            }

        }
        return false;
    }


    public List<Student> getMemberList(Group group) {
        List<Student> memberList = new ArrayList<>();
        //todo
        //根据学生id获取队伍
        if (group.getMember1Id() != null){
            memberList.add(studentRepository.getReferenceById(group.getMember1Id()));
        }
        if (group.getMember2Id() != null){
            memberList.add(studentRepository.getReferenceById(group.getMember2Id()));
        }
        if (group.getMember3Id() != null){
            memberList.add(studentRepository.getReferenceById(group.getMember4Id()));
        }
        if (group.getMember4Id() != null){
            memberList.add(studentRepository.getReferenceById(group.getMember4Id()));
        }
        return memberList;
    }

    public List<Group> getGroupsList() {

        return groupRepository.getGroups();
    }

    /**
     * 获取star列表
     */

    public List<Room> getStarList(Group group) {
        return group.getRoomStarList();
    }


}