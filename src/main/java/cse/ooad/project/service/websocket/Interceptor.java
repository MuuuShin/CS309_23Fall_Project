package cse.ooad.project.service.websocket;

import cse.ooad.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


@Component
public class Interceptor implements HandshakeInterceptor {

    /**
     * 握手前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");
        String sessionId;
        URL url = request.getURI().toURL();
        String query = url.getQuery();
        String[] split = query.split("&");
        HashMap<String, String> map = new HashMap<>();
        Arrays.stream(split).forEach(t -> {
            String[] split1 = t.split("=");
            map.put(split1[0], split1[1]);
        });

        sessionId = map.get("sessionid");
        if (sessionId == null) {

            return false;
        }else {
            Claims claims = JwtUtils.parseJWT(sessionId);
            attributes.put("session_id", claims.get("id"));
            return true;
        }

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
