package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Msg;
import cse.ooad.project.model.Password;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.PasswordRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.model.*;
import cse.ooad.project.repository.*;
import cse.ooad.project.utils.MessageStatus;
import cse.ooad.project.utils.MessageType;
import cse.ooad.project.utils.RoomType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private PasswordRepository passwordRepository;


    /**
     * 修改学生的个人介绍，这个介绍会被用在搜索上
     * @param student 修改的具体内容有：introduce，awakeTime，sleepTime
     * @return
     */
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

    /**
     * 修改学生的密码
     * @param student
     * @return
     */
    public Student updatePassword(Student student, String oldPassword) {
        return studentRepository.save(student);
    }

    /**
     * 传入一个学生id和队伍名，由这个学生来创建队伍，一开始只有他一个人在队里
     * @param id 学生id
     * @param name 队伍名
     */
    @Transactional
    public Group createGroup(Long id, String name, String intro) {
        Student student = studentRepository.getStudentByStudentId(id);
        if(student == null){
            return null;
        }
        if (student.getGroup() != null){
            return null;
        }
        Group group = new Group();
        group.setName(name);
        group.setMemberList(new ArrayList<>());
        group.setIntro(intro);
        group.setLeader(id);
        group.getMemberList().add(student);
        group = groupRepository.save(group);
        student.setGroupId(group.getGroupId());
        studentRepository.save(student);
        msgService.sendSystemMsg(group.getMemberList(), "你创建了队伍" + group.getName());
        return group;

    }



    /**
     * 会判断学生类型、队伍人数来决定能否加入
     *
     * @param studentId 学生id
     * @param groupId  队伍id
     * @return 是否加入成功
     */

    @Transactional
    public boolean joinGroup(Long studentId, Long groupId) {

        //获得阶段
        Student student = studentRepository.getStudentByStudentId(studentId);
        Group group = groupRepository.getGroupByGroupId(groupId);
        int stage = timelineService.getStage(student.getType());
        ArrayList<Student> list = new ArrayList<>();
        list.add(student);
        // 第一个阶段可以 第三个阶段可以
        if (stage != 1 && stage != 3) {
            msgService.sendSystemMsg(list,"你加入队伍" + group.getName()+ "失败, 当前阶段不允许加入队伍" );
            return false;
        }
        Student leader = studentRepository.getStudentByStudentId(group.getLeader());
        if (Objects.equals(leader.getType(), student.getType())) {
            //判断人数合不合适
            if (group.getMemberList().size() == 4) {
                msgService.sendSystemMsg(list,"你加入队伍" + group.getName()+ "失败, 队伍人数已满" );
                return false;
            }
            //判断有没有加入队伍
            if (student.getGroup() != null) {
                msgService.sendSystemMsg(list,"你加入队伍" + group.getName()+ "失败, 你已经加入了一个队伍" );
                return false;
            }
            msgService.sendSystemMsg(group.getMemberList(), student.getName() + "加入了队伍");
            student.setGroupId(groupId);

            msgService.sendSystemMsg(list, "你加入了队伍" + group.getName());
            studentRepository.save(student);
            return true;
        }
        msgService.sendSystemMsg(list,"你加入队伍" + group.getName()+ "失败, 你的类型和队伍类型不匹配" );
        return false;
    }


    @Transactional
    public Boolean memberLeave(Long userId, Long kickedId) {
        Student student = studentRepository.getStudentByStudentId(kickedId);
        Group group = student.getGroup();

        if (group == null) {
            return false;
        }

        Long leaderId = group.getLeader();

        if (Objects.equals(leaderId, userId)) {
            student.setGroupId(null);
            group.getMemberList().remove(student);
            studentRepository.save(student);
            msgService.sendSystemMsg(student, "你被踢出了队伍!");
            msgService.sendSystemMsg(group.getMemberList(), student.getName() + "被踢出了队伍!");
            return true;
        }
        return false;
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
            //如果是队长
            if (group.getMemberList().size() == 1) {
                //如果是最后一个人
                groupRepository.delete(group);
                student.setGroupId(null);
                studentRepository.save(student);
                return true;
            }
            //如果不是最后一个人
            if(Objects.equals(group.getMemberList().get(0).getStudentId(), id)){
                group.setLeader(group.getMemberList().get(1).getStudentId());
            }else{
                group.setLeader(group.getMemberList().get(0).getStudentId());
            }
            group.getMemberList().remove(student);
            groupRepository.save(group);
            student.setGroupId(null);
            studentRepository.save(student);
            msgService.sendSystemMsg(group.getMemberList(), student.getName() + "离开了队伍");
            return true;
        }
        student.setGroupId(null);
        studentRepository.save(student);
        msgService.sendSystemMsg(group.getMemberList(), student.getName() + "离开了队伍");
        return true;
    }

    /**
     * 发送聊天信息
     *
     * @param srcId   发送者id
     * @param sendToId 接收者id
     * @param message 信息内容
     */
    public void sendMessage(Long srcId, Long sendToId, String message) {
        Msg msg = new Msg();
        Student src = studentRepository.getStudentByStudentId(srcId);
        Student sendTo = studentRepository.getStudentByStudentId(sendToId);
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msg.setBody(message);
        msg.setSrcId(src.getStudentId());
        msg.setDstId(sendTo.getStudentId());
        msgService.saveAndForwardMsg(msg);
    }

    /**
     * 获取聊天信息列表
     *
     * @param id   学生id
     * @param toId 对方id
     * @return 聊天信息列表
     */
    public List<Msg> getMsgList(Long id, Long toId) {
        return msgRepository.getMsgsBySrcIdAndDstId(id, toId);
    }

    /**
     * 获取所有学生
     *
     * @return 学生列表
     */

    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }


    /**
     * 发送入队申请
     *
     * @param studentId 学生id
     * @param leaderId  队长id
     * @param message   申请信息
     *
     * @return 学生信息
     */
    //发送入队申请
    @Transactional
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
        if (group.getRoom() != null && RoomType.valueOf(group.getRoom().getType()).getCapacity()
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
        msgService.saveAndForwardMsg(msg);
        return true;
    }

    /**
     * 获取申请消息列表，只有队长才能获取
     *
     * @param leaderId 队长id
     * @return 申请消息列表
     */
    //获取申请消息列表
    public List<Msg> getApplyList(Long leaderId) {
        return msgRepository.getMsgsByDstIdAndStatusAndType(leaderId, MessageStatus.UNREAD.getStatusCode(), MessageType.APPLY.typeCode);
    }


    //处理申请

    /**
     * 处理申请
     * @param msgId 申请消息id
     * @param isAgree 是否同意
     * @param studentId 学生id,这个id是处理消息的学生的id
     * @return
     */
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
        if (msg.getStatus() == MessageStatus.READ_AND_REJECTED.getStatusCode()||msg.getStatus() == MessageStatus.READ_AND_ACCEPTED.getStatusCode()) {
            return false;
        }
        if (isAgree) {
            if (joinGroup(msg.getSrcId(), studentRepository.getStudentByStudentId(studentId).getGroupId())) {
                msg.setStatus(MessageStatus.READ_AND_ACCEPTED.getStatusCode());
                msgRepository.save(msg);
                return true;
            }
        }
        msg.setStatus(MessageStatus.READ_AND_REJECTED.getStatusCode());
        msgRepository.save(msg);
        return true;
    }


    /**
     * 进行评论评论
     * @param comment 评论
     * @return 评论
     */
    public Comment saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    /**
     * 删除评论，只有老师和自己能删除
     * @param id 评论id
     * @param userId 用户id
     * @return 是否删除成功
     */
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



    public boolean updatePassword(String account,String oldPassword, String password, boolean isTeacher) {
        if (!isTeacher&&!passwordRepository.findPasswordByAccount(account).getPassword().equals(oldPassword)) {
            return false;
        }
        Password pass = new Password(account, password);
        passwordRepository.save(pass);
        return true;
    }
}

