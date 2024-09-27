package com.test.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓이 handshake를 하기 위해 연결하는 endpoint이다.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 -> 클라이언트로 발행하는 메시지에 대한 endpoint 설정 : 구독
        // 클라이언트가 sub 경로로 구독하면 서버는 이 결로로 발행된 모든 메시지를 해당 구독자에게 전달
        // /sub 으로 시작하는 경로를 통해 다른 클라이언트에게 전달한다.
        registry.enableSimpleBroker("/sub");

        // 클라이언트 -> 서버로 발행하는 메시지에 대한 endpoint 설정 : 구독에 대한 메시지
        // 메시지를 서버로 보낼 때 /pub 으로 시작하는 경로를 사용해서 보낸다.
        // ex /pub/chat/roomId
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
