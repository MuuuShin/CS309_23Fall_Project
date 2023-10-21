package cse.ooad.project.service;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.GroupRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentService studentService;

    final Long STARLIMITE = 6L;

    /**
     * 传入一个学生和队伍名，由这个学生来创建队伍，一开始只有他一个人在队里
     */
    public void createGroup(Student student, String name) {
        Group group = new Group();
        group.setName(name);
        group.setLeader(student.getName());
        group.setMember1Id(student.getStudentId());

    }


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


    /**
     * 学生脱队，队长脱队后顺序继承，是最后一人则解散
     *
     * @param group
     * @param student 脱队的学生
     */
    public void memberLeave(Group group, Student student) {
    }

    public List<Student> getMemberList(Group group){
        return null;
    }

    /**
     * 获取star列表
     */

    public List<Room> getStarList(Group group){
        return group.getRoomStarList();
    }


}
