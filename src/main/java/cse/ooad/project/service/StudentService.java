package cse.ooad.project.service;



import cse.ooad.project.model.ChatRecord;
import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.StudentRepository;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CommentRepository commentRepository;


    public void changeIntroduce(Student student, LocalTime awakeTime,LocalTime sleepTime,String intro){}

    public void changePassword(Student student,String oldPsw ,String newPsw){}

    /**
     * 传入一个学生和队伍名，由这个学生来创建队伍，一开始只有他一个人在队里
     */
    public void createGroup(Student student, String name) {
        Group group = new Group();
        group.setName(name);
        group.setLeader(student.getName());
        group.setMember1Id(student.getStudentId());
    }

    public boolean joinTeam(Student student, Group group){
        return false;
    }

    /**
     * 学生脱队，队长脱队后顺序继承，是最后一人则解散
     *会自动给队长发退队消息
     * @param student 脱队的学生
     */
    public void memberLeave(Student student) {
    }

    public void sendMessage(Student src, Student sendTo, String message){}

    public List<ChatRecord> getChatRecord(Student user, Student target){
        return null;
    }


    public void saveComment(Comment comment){
    }

    public void deleteComment(Comment comment){
    }









}

