package cse.ooad.project.service.websocket;

import com.google.gson.Gson;
import cse.ooad.project.model.Msg;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.service.MsgService;
import cse.ooad.project.utils.MessageStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class HttpAuthHandler extends TextWebSocketHandler {



    @Autowired
    MsgRepository msgRepository;

    @Autowired
    MsgService msgService;

    @Autowired
    Gson gson;

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = session.getAttributes().get("session_id").toString();
        if (userId != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(userId, session);
            //发送所有未读消息
            List<Msg> msgs = msgService.getMsgByDistId(Long.valueOf(userId));
            msgs.sort(Comparator.comparing(Msg::getTimestamp));
            msgs.forEach(t -> {
                try {
                    if (t.getStatus() == MessageStatus.UNREAD.getStatusCode()){
                        msgService.saveAndForwardMsg(t);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            session.sendMessage(new TextMessage("server 发送给 " + userId + " 消息 " + "连接成功" + " " + LocalDateTime.now()));
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object sessionId = session.getAttributes().get("session_id");
        //todo 发来的payload会是一个Msg的Json格式, 因此直接转换为Msg
        Msg msg = gson.fromJson(payload, Msg.class);
        msg.setStatus(MessageStatus.UNREAD.getStatusCode());
        msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        msgService.saveAndForwardMsg(msg);
        System.out.println("server 接收到 " + sessionId + " 发送的 " + payload);
    }


    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object sessionId = session.getAttributes().get("session_id");
        System.out.println(sessionId);
        if (sessionId != null) {
            System.out.println(sessionId + "断开连接");
            // 用户退出，移除缓存
            WsSessionManager.remove(sessionId.toString());
        }
    }


}
