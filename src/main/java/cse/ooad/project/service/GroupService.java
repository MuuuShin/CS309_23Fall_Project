package cse.ooad.project.service;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
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
        return false;
    }




    public List<Student> getMemberList(Group group){
        return null;
    }

    public List<Group> getGroupsList(){
        return null;
    }

    /**
     * 获取star列表
     */

    public List<Room> getStarList(Group group){
        return group.getRoomStarList();
    }


}
