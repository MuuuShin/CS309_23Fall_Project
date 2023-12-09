package cse.ooad.project.service;


import com.google.gson.Gson;
import cse.ooad.project.model.Msg;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.service.websocket.WsSessionManager;
import cse.ooad.project.model.Student;
import cse.ooad.project.utils.MessageStatus;
import cse.ooad.project.utils.MessageType;
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

    public Msg saveMsg(Msg msg) {
        forwardMsg(msg);
        return msgRepository.save(msg);

    }


    public void sendSystemMsg(List<Student> students, String content){
        students.forEach(t->{
            Msg msg = new Msg();
            msg.setSrcId(0L);
            msg.setDstId(t.getStudentId());
            msg.setBody(content);
            msg.setType(MessageType.SYSTEM.typeCode);
            msg.setStatus(MessageStatus.UNREAD.getStatusCode());
            msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
            forwardMsg(msg);
        });

    }

    public void forwardMsg(Msg msg) {
        String session = WsSessionManager.USER_POOL.get(msg.getDstId());
        try {
            //在线就转发消息
            if (session != null) {
                WebSocketSession webSocketSession = WsSessionManager.get(session);
                if (webSocketSession != null) {
                    webSocketSession.sendMessage(new TextMessage(gson.toJson(msg)));
                    msg.setStatus(MessageStatus.Read.getStatusCode());
                }
            }
        } catch (Exception e) {
            System.out.println("转发信息失败");
            e.printStackTrace();
        }
        msgRepository.save(msg);
    }
}
