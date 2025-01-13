package com.example.emojihuntbattle.WebSocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {
    private String message;

    // Constructor
    public Message(String message) {
        this.message = message;
    }
}
