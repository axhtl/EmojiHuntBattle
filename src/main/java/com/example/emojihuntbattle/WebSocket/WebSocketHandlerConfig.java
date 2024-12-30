package com.example.emojihuntbattle.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketHandlerConfig {

    @Bean
    public GameWebSocketHandler gameWebSocketHandler() {
        return new GameWebSocketHandler();
    }
}
