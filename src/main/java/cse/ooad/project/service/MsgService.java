package cse.ooad.project.service;


import com.google.gson.Gson;
import cse.ooad.project.model.Msg;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.service.websocket.WsSessionManager;
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

    public void saveMsg(Msg msg) {
        msgRepository.save(msg);
        forwardMsg(msg);
    }

    public void forwardMsg(Msg msg) {
        String session = WsSessionManager.USER_POOL.get(msg.getDstId());
        try {
            //在线就转发消息
            if (session != null) {
                WebSocketSession webSocketSession = WsSessionManager.get(session);
                if (webSocketSession != null) {
                    webSocketSession.sendMessage(new TextMessage(gson.toJson(msg)));
                }
            }
        } catch (Exception e) {
            System.out.println("转发信息失败");
            e.printStackTrace();
        }
    }
}
