package cse.ooad.project.service;



import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Msg;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.StudentRepository;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TimelineService timelineService;

    @Autowired
    MsgService msgService;

    @Autowired
    MsgRepository msgRepository;


    public void changeIntroduce(Student student, Time awakeTime,Time sleepTime,String intro){
        student.setAwakeTime(awakeTime);
        student.setSleepTime(sleepTime);
        student.setIntro(intro);
        studentRepository.save(student);
    }

    public void changePassword(Student student,String oldPsw ,String newPsw){
        if (Objects.equals(student.getPassword(), oldPsw)){
            student.setPassword(newPsw);
            studentRepository.save(student);
        }
    }

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
     * 会判断学生类型、队伍人数来决定能否加入
     * @param student
     * @param group
     * @return
     */
    public boolean joinGroup(Student student, Group group){
        //获得阶段
        int stage = timelineService.getStage(student.getType());
        //不记得哪个阶段能加队伍了
        Student leader = studentRepository.getStudentByStudentId(group.getMember1Id());
        if (leader.getType() == student.getType()){
            //todo 判断人数合不合适

            return true;
        }
        return false;
    }

    /**
     * 学生脱队，队长脱队后顺序继承，是最后一人则解散
     *会自动给队长发退队消息
     * @param student 脱队的学生
     */
    public void memberLeave(Student student) {
        Group group = student.getGroup();
        if (Objects.equals(student.getStudentId(), group.getMember1Id())){
            group.setMember1Id(null);
            student.setGroup(null);
            student.setGroupId(null);
        }
        if (Objects.equals(student.getStudentId(), group.getMember2Id())){
            group.setMember2Id(null);
            student.setGroup(null);
            student.setGroupId(null);
        }
        if (Objects.equals(student.getStudentId(), group.getMember3Id())){
            group.setMember3Id(null);
            student.setGroup(null);
            student.setGroupId(null);
        }
        if (Objects.equals(student.getStudentId(), group.getMember4Id())){
            group.setMember4Id(null);
            student.setGroup(null);
            student.setGroupId(null);
        }
        groupRepository.save(group);
        studentRepository.save(student);
        //todo 发送退队消息
    }

    public void sendMessage(Student src, Student sendTo, String message){
        Msg msg = new Msg();
        msg.setUnread(true);
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msg.setBody(message);
        msg.setSrcId(src.getStudentId());
        msg.setDstId(sendTo.getStudentId());
        msgService.saveMsg(msg);

    }

    public List<Msg> getMsgList(Student user, Student target){
        msgRepository.getMsgsBySrcIdAndDstId(user.getStudentId(), target.getStudentId());
        return null;
    }


    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }









}

