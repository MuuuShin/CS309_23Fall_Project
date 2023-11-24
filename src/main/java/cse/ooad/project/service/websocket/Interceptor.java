package cse.ooad.project.service.websocket;

import cse.ooad.project.service.StudentService;
import java.util.Arrays;
import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


@Component
public class Interceptor implements HandshakeInterceptor {


    @Autowired
    StudentService studentService;
    /**
     * 握手前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");

        /*String sessionId = request.getHeaders().get("sessionId").get(0);*/
        String hostName = request.getRemoteAddress().getHostName();
        String sessionId = hostName + String.valueOf((int) (Math.random() * 1000));
        if (Strings.isNotBlank(sessionId)) {
            // 放入属性域
            attributes.put("session_id", sessionId);
            System.out.println("用户" + sessionId);
            return true;
        }
        return false;
    }

    /**
     * 握手后
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手完成");
    }

}
