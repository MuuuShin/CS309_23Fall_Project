package cse.ooad.project.service;


import com.google.gson.Gson;
import cse.ooad.project.model.Msg;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.service.websocket.WsSessionManager;
import cse.ooad.project.model.Student;
import cse.ooad.project.utils.MessageStatus;
import cse.ooad.project.utils.MessageType;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class MsgService {


    @Autowired
    MsgRepository msgRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    Gson gson;





    /**
     * 批量发送系统消息
     * @param students 学生列表
     * @param content 消息内容
     */
    public void sendSystemMsg(List<Student> students, String content){
        students.forEach(t->{
            sendSystemMsg(t,content);
        });

    }

    public void sendSystemMsg(Student student, String content) {
        Msg msg = new Msg();
        msg.setSrcId(0L);
        msg.setDstId(student.getStudentId());
        msg.setBody(content);
        msg.setType(MessageType.SYSTEM.typeCode);
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        saveAndForwardMsg(msg);
    }


    /**
     * 保存消息，同时转发消息
     * @param msg 消息
     */
    public void saveAndForwardMsg(Msg msg) {
        try {
            //在线就转发消息
            if (msg != null) {
                WebSocketSession webSocketSession = WsSessionManager.get(msg.getDstId().toString());
                if (webSocketSession != null) {
                    System.out.println("转发信息"+msg.getBody()+"给"+msg.getDstId());
                    webSocketSession.sendMessage(new TextMessage(gson.toJson(msg)));
                    msg.setStatus(MessageStatus.READ.getStatusCode());
                }
                msgRepository.save(msg);
            }
        } catch (Exception e) {
            System.out.println("转发信息失败");
            e.printStackTrace();
        }

    }


    public void saveMsg(Msg msg) {
        try {
            if (msg != null) {
                WebSocketSession webSocketSession = WsSessionManager.get(msg.getDstId().toString());
                if (webSocketSession != null) {
                    System.out.println("转发信息" + msg.getBody() + "给" + msg.getDstId());
                    msg.setStatus(MessageStatus.READ.getStatusCode());
                }
                msgRepository.save(msg);
            }
        } catch (Exception e) {
            System.out.println("转发信息失败");
            e.printStackTrace();
        }
    }


    public void sendMsg(Msg msg) {
        try {
            WebSocketSession webSocketSession = WsSessionManager.get(msg.getDstId().toString());
            if (webSocketSession != null) {
                System.out.println("发信息" + msg.getBody() + "给" + msg.getDstId());
                webSocketSession.sendMessage(new TextMessage(gson.toJson(msg)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int hasUnreadMsgNum(Long studentId) {
        return msgRepository.countByDstIdAndStatus(studentId, MessageStatus.UNREAD.getStatusCode());
    }


    /**
     * 获取消息列表
     * @param distId 接收者id
     * @return 消息列表
     */
    public List<Msg> getMsgByDistId(Long distId) {
        return msgRepository.getMsgsByDstId(distId);
    }

    public List<Msg> getMsgBySrcId(Long srcId) {
        return msgRepository.getMsgsBySrcId(srcId);
    }


    public boolean updateMsgStatus(Long userId, Long oppoId) {
        List<Msg> msgs = msgRepository.getMsgsByDstIdAndSrcIdAndStatus(userId, oppoId, MessageStatus.UNREAD.getStatusCode());
        msgs.forEach(t->{
            t.setStatus(MessageStatus.READ.getStatusCode());
            msgRepository.save(t);
        });
        return true;
    }
}
