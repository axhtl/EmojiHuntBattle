package com.example.emojihuntbattle.WebSocket;

import com.example.emojihuntbattle.Service.BroadcastMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        // 로그 추가하여 플레이어가 잘 추가되었는지 확인
        System.out.println("Total sessions: " + sessions.size());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

//        // 메시지를 모든 클라이언트에게 브로드캐스트
//        for (WebSocketSession webSocketSession : sessions) {
//            if (webSocketSession.isOpen()) {
//                webSocketSession.sendMessage(new TextMessage("Echo: " + payload));
//            }
//        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    // 방 준비 완료 시 모든 클라이언트에 메시지 브로드캐스트
    public void broadcastRoomReady(String message) {
        // 메시지를 JSON 형식으로 변환
        try {
            // ObjectMapper를 사용해 메시지를 JSON 형식으로 변환
            ObjectMapper objectMapper = new ObjectMapper();

            // 메시지를 JSON으로 변환
            String jsonMessage = objectMapper.writeValueAsString(new Message(message));

            // 모든 세션에 메시지 전송
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        System.out.println("Sending message to session: " + session.getId());
                        session.sendMessage(new TextMessage(jsonMessage)); // JSON 메시지 전송
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Session " + session.getId() + " is closed.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void broadcastAnswerPosition(Long gameRoomId, int round, int answerPosition) {
            System.out.println("Broadcasting message to all clients.");

            // 메시지 객체 생성
            BroadcastMessage message = new BroadcastMessage(gameRoomId, round, answerPosition);

            // 메시지를 JSON 형식으로 변환
            try {
                // ObjectMapper를 사용해 메시지를 JSON 형식으로 변환
                ObjectMapper objectMapper = new ObjectMapper();

                // 메시지를 JSON으로 변환
                String jsonMessage = objectMapper.writeValueAsString(message);

                // 모든 세션에 메시지 전송
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        try {
                            System.out.println("Sending message to session: " + session.getId());
                            session.sendMessage(new TextMessage(jsonMessage)); // JSON 메시지 전송
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Session " + session.getId() + " is closed.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("Broadcasting message to all clients.");
//        for (WebSocketSession session : sessions) {
//            if (session.isOpen()) {
//                try {
//                    System.out.println("Sending message to session: " + session.getId());
//                    session.sendMessage(new org.springframework.web.socket.TextMessage(message));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("Session " + session.getId() + " is closed.");
//            }
//        }
    }