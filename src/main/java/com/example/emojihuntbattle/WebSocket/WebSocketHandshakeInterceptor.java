package com.example.emojihuntbattle.WebSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    // WebSocket 연결 전에 호출되는 메서드
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        // 필요하면 여기에 요청/응답을 기반으로 연결 검증을 추가할 수 있음
        System.out.println("Before Handshake: " + request.getURI());
        return true; // true를 반환해야 핸드셰이크가 진행됩니다.
    }

    // WebSocket 연결 후에 호출되는 메서드
    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {

        System.out.println("After Handshake");
    }
}