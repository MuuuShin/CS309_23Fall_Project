package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Msg;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.utils.MessageStatus;
import cse.ooad.project.utils.MessageType;
import cse.ooad.project.utils.RoomType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RegionRepository regionRepository;


    public Student updateIntroduce(Student student) {
        Student old = studentRepository.getStudentByStudentId(student.getStudentId());
        student.setGender(old.getGender());
        student.setType(old.getType());
        student.setAccount(old.getAccount());
        student.setGroupId(old.getGroupId());
        student.setStudentId(old.getStudentId());
        student.setName(old.getName());
        return studentRepository.save(student);
    }

    public Student changePassword(Student student) {
        return studentRepository.save(student);
    }

    /**
     * 传入一个学生id和队伍名，由这个学生来创建队伍，一开始只有他一个人在队里
     */
    @Transactional
    public Group createGroup(Long id, String name) {
        Student student = studentRepository.getStudentByStudentId(id);
        if (student.getGroup() != null){
            return null;
        }
        Group group = new Group();
        group.setName(name);
        group.setMemberList(new ArrayList<>());

        group.setLeader(id);
        group.getMemberList().add(student);
        group = groupRepository.save(group);
        student.setGroupId(group.getGroupId());
        studentRepository.save(student);
        //todo 系统发送创建队伍成功消息
        msgService.sendSystemMsg(group.getMemberList(), "你创建了队伍" + group.getName());
        return group;

    }

    /**
     * 会判断学生类型、队伍人数来决定能否加入
     *
     * @param studentId
     * @param groupId
     * @return
     */

    @Transactional
    public boolean joinGroup(Long studentId, Long groupId) {
        //todo 更加详细的测试这个方法
        //获得阶段
        Student student = studentRepository.getStudentByStudentId(studentId);
        Group group = groupRepository.getGroupByGroupId(groupId);
        int stage = timelineService.getStage(student.getType());

        // 第一个阶段可以 第三个阶段可以
        if (stage != 1 && stage != 3) {
            return false;
        }
        Student leader = studentRepository.getStudentByStudentId(group.getLeader());
        if (Objects.equals(leader.getType(), student.getType())) {
            //判断人数合不合适
            if (group.getMemberList().size() == 4) {
                return false;
            }
            //判断有没有加入队伍
            if (student.getGroup() != null) {
                return false;
            }
            msgService.sendSystemMsg(group.getMemberList(), student.getName() + "加入了队伍");
            student.setGroupId(groupId);
            ArrayList<Student> list = new ArrayList<>();
            list.add(student);
            msgService.sendSystemMsg(list, "你加入了队伍" + group.getName());
            studentRepository.save(student);
            return true;
        }
        return false;
        //todo 系统发送加入队伍成功/失败消息 针对加入的这个人和队伍里其他人 发送的消息应该不一样
    }

    /**
     * 学生脱队，队长脱队后顺序继承，是最后一人则解散 会自动给队长发退队消息
     *
     * @param id 脱队的学生
     */
    @Transactional
    public Boolean memberLeave(Long id) {
        Student student = studentRepository.getStudentByStudentId(id);
        Group group = student.getGroup();

        if (group == null) {
            return false;
        }
        if (Objects.equals(group.getLeader(), id)) {
            return false;
        }
        student.setGroupId(null);
        studentRepository.save(student);
        msgService.sendSystemMsg(group.getMemberList(), student.getName() + "离开了队伍");
        return true;
    }

    public void sendMessage(Long srcId, Long sendToId, String message) {
        Msg msg = new Msg();
        Student src = studentRepository.getStudentByStudentId(srcId);
        Student sendTo = studentRepository.getStudentByStudentId(sendToId);
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msg.setBody(message);
        msg.setSrcId(src.getStudentId());
        msg.setDstId(sendTo.getStudentId());
        msgService.forwardMsg(msg);
    }

    public List<Msg> getMsgList(Long id, Long toId) {
        return msgRepository.getMsgsBySrcIdAndDstId(id, toId);
    }


    //发送入队申请
    public boolean sendApply(Long studentId, Long leaderId, String message) {
        Msg msg = new Msg();
        Student src = studentRepository.getStudentByStudentId(studentId);
        Student leader = studentRepository.getStudentByStudentId(leaderId);
        Group group = groupRepository.getGroupByGroupId(leader.getGroupId());
        //判断是否有这个队伍
        if (group == null) {
            return false;
        }
        //是否已经入队
        if (src.getGroup() != null){
            return false;
        }
        //队伍满员
        if(group.getMemberList().size() == 4) {
            return false;
        }
        //如果队伍选了房间且已经满员
        if (group.getRoom() != null && RoomType.valueOf(group.getRoom().getType() + "").getCapacity()
            == group.getMemberList().size()) {
            return false;
        }
        //已经处于队伍中
        if (group.getMemberList().contains(src)) {
            return false;
        }
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msg.setBody(message);
        msg.setSrcId(src.getStudentId());
        msg.setDstId(leader.getStudentId());
        msg.setType(MessageType.APPLY.typeCode);
        msgService.forwardMsg(msg);
        return true;
    }

    //获取申请消息列表
    public List<Msg> getApplyList(Long leaderId) {
        return msgRepository.getMsgsByDstIdAndStatusAndType(leaderId, MessageStatus.UNREAD.getStatusCode(), MessageType.APPLY.typeCode);
    }


    //处理申请
    public boolean handleApply(Long msgId, boolean isAgree, Long studentId) {
        Msg msg = msgRepository.getMsgByMsgId(msgId);
        if (msg == null) {
            return false;
        }
        if (!Objects.equals(studentId, msg.getDstId())) {
            return false;
        }

        if (msg.getType() != MessageType.APPLY.typeCode) {
            return false;
        }
        if (msg.getStatus() != MessageStatus.UNREAD.getStatusCode()) {
            return false;
        }
        if (isAgree) {
            if (joinGroup(msg.getSrcId(), msg.getDstId())){
                msg.setStatus(MessageStatus.READ_AND_ACCEPTED.getStatusCode());
                msgRepository.save(msg);
                return true;
            }else {
                return false;
            }
        }
        msg.setStatus(MessageStatus.READ_AND_REJECTED.getStatusCode());
        msgRepository.save(msg);
        return false;
    }



    public Comment saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public Boolean deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.getCommentByCommentId(id);
        boolean isTeacher = userId < 2 * 100000000;
        boolean isStudent = userId >= 2 * 100000000;
        if (isTeacher) {
            comment.setDisabled(true);
            commentRepository.save(comment);
            return true;
        }
        if (isStudent) {
            //防止删除根评论
            if (comment.getUserId() == null) {
                return false;
            }
            //删除对应的评论
            if (!Objects.equals(comment.getUserId(), userId)) {
                return false;
            }
            comment.setDisabled(true);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }


}

