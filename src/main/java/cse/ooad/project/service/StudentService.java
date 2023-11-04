package cse.ooad.project.service;



import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Msg;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.utils.MessageStatus;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public void changeIntroduce(Student student){
        studentRepository.save(student);
    }

    public void changePassword(Student student){
        studentRepository.save(student);
    }

    /**
     * 传入一个学生id和队伍名，由这个学生来创建队伍，一开始只有他一个人在队里
     */
    @Transactional
    public void createGroup(Long id, String name) {
        Group group = new Group();
        group.setName(name);
        group.setMemberList(new ArrayList<>());
        Student student = studentRepository.getStudentByStudentId(id);
        group.setLeader(id);
        group.getMemberList().add(student);
        group = groupRepository.save(group);
        student.setGroupId(group.getGroupId());
        student = studentRepository.save(student);
       // student.setGroupId(group.getGroupId());
    }

    /**
     * 会判断学生类型、队伍人数来决定能否加入
     * @param studentId
     * @param groupId
     * @return
     */

    @Transactional
    public boolean joinGroup(Long studentId, Long groupId){
        //获得阶段
        Student student = studentRepository.getStudentByStudentId(studentId);
        Group group = groupRepository.getGroupByGroupId(groupId);
        int stage = timelineService.getStage(student.getType());
        //不记得哪个阶段能加队伍了
        Student leader = studentRepository.getStudentByStudentId(group.getLeader());
        if (leader.getType() == student.getType()){
            //todo 判断人数合不合适
            if (group.getMemberList().size() == 4){
                return false;
            }
            group.getMemberList().add(student);
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
        student.setGroupId(null);
        studentRepository.save(student);
        //todo 发送退队消息
    }

    public void sendMessage(Long srcId, Long sendToId, String message){
        Msg msg = new Msg();
        Student src = studentRepository.getStudentByStudentId(srcId);
        Student sendTo = studentRepository.getStudentByStudentId(sendToId);
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msg.setBody(message);
        msg.setSrcId(src.getStudentId());
        msg.setDstId(sendTo.getStudentId());
        msgService.saveMsg(msg);

    }

    public List<Msg> getMsgList(Long id, Long toId){
        msgRepository.getMsgsBySrcIdAndDstId(id, toId);
        return null;
    }


    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }









}

