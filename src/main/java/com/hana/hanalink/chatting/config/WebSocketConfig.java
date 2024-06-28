package com.hana.hanalink.chatting.config;

import com.hana.hanalink.chatting.handler.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // 웹 소켓을 사용하도록 정의
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(signalingSocketHandler(), "/room") // 직접 구현한 웹소켓 핸들러 (signalingSocketHandler)를 웹소켓이 연결될 때, Handshake할 주소 (/room)
                .setAllowedOriginPatterns("*"); // 클라이언트에서 웹 소켓 서버에 요청하는 모든 요청을 수락, CORS 방지
        // 웹소켓을 지원하지 않는 브라우저 환경에서도 비슷한 경험을 할 수 있는 기능을 제공
    }

    @Bean
    public WebSocketHandler signalingSocketHandler() { // WebSocketHandler을 웹 소켓 핸들러로 정의
        return new WebSocketHandler();
    }
}