package cse.ooad.project.service.websocket;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;


@Component
public class WsSessionManager {
    /**
     * 保存连接 session 的地方
     */
    public static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();
    /**
     * 每个登录上来的学生会对应一个session，这个session会对应一个websocketSession
     */
    public static ConcurrentHashMap<String, String> USER_POOL = new ConcurrentHashMap<>();


    {
        USER_POOL.put("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAwMDAwMDA0LCJpc1RlYWNoZXIiOmZhbHNlLCJ1c2VybmFtZSI6IjEyMTExNzEyIiwiaWF0IjoxNzAyMTMzNzQ2LCJleHAiOjE3MDI0OTM3NDZ9.TJzZEIybGy9q1kc8urZiKO8GjhXL5wlzUh_-OlWoJB4", "200000004");
        USER_POOL.put("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAwMDAwMDAxLCJpc1RlYWNoZXIiOmZhbHNlLCJ1c2VybmFtZSI6IjEyMTEwNDI1IiwiaWF0IjoxNzAyMTI5NDM0LCJleHAiOjE3MDI0ODk0MzR9.lUGUz8dtYF37VyUaiTZgzXrg_0HvYh0iAd97Y67708U", "200000001");
    }

    /**
     * 添加 session
     *
     * @param key
     */
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    public static void addLoginUser(Long userId, String session_id) {
        // 添加 session
        USER_POOL.put(session_id, String.valueOf(userId));
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }



    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        System.out.println(key);
        System.out.println(SESSION_POOL);
        return SESSION_POOL.get(key);
    }
}
