package org.example.ebookstore.config;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
//    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
//
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic","/user");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS().setInterceptors(new WebSocketHandshakeInterceptor());
//    }
//
//
//    // 定义一个拦截器来捕获 WebSocket 握手请求中的 session 信息
//    public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
//
//        @Override
//        public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
//                                       org.springframework.http.server.ServerHttpResponse response,
//                                       org.springframework.web.socket.WebSocketHandler wsHandler,
//                                       Map<String, Object> attributes) throws Exception {
//            if (request instanceof org.springframework.http.server.ServletServerHttpRequest) {
//                HttpSession httpSession = ((org.springframework.http.server.ServletServerHttpRequest) request)
//                        .getServletRequest().getSession();
//                if (httpSession != null) {
//                    // 将 session 保存到 ConcurrentHashMap 中
//                    sessions.put(httpSession.getId(), httpSession);
//                    // 将 sessionId 添加到 WebSocket 属性中，供后续使用
//                    attributes.put("sessionId", httpSession.getId());
//                }
//            }
//            return true;
//        }
//
//        @Override
//        public void afterHandshake(org.springframework.http.server.ServerHttpRequest request,
//                                   org.springframework.http.server.ServerHttpResponse response,
//                                   org.springframework.web.socket.WebSocketHandler wsHandler,
//                                   Exception exception) {
//            // 握手完成后的处理（如果需要）
//        }
//    }
//
//    // 提供一个获取 session 的方法
//    public static HttpSession getSession(String sessionId) {
//        return sessions.get(sessionId);
//    }
//
//    // 提供一个移除 session 的方法（当连接关闭时）
//    public static void removeSession(String sessionId) {
//        sessions.remove(sessionId);
//    }
//
//    // 监听 SessionDisconnectEvent 并在断开连接时删除 session
//    @Component
//    public class WebSocketEventListener {
//
//        @EventListener
//        public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//            // 从事件中获取 sessionId
//            String sessionId = headerAccessor.getSessionId();
//
//            if (sessionId != null) {
//                // 断开连接时移除对应的 session
//                WebSocketConfig.removeSession(sessionId);
//            }
//        }
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}