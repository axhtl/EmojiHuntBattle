package com.example.emojihuntbattle.WebSocket;

import com.example.emojihuntbattle.Domain.GameRoom;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // 메시지를 모든 클라이언트에게 브로드캐스트
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("Echo: " + payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    // 방 준비 완료 시 모든 클라이언트에 메시지 브로드캐스트
    public void broadcastRoomReady(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new org.springframework.web.socket.TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}