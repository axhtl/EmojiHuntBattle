package com.example.emojihuntbattle.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastMessage {
    private Long gameRoomId;
    private int round;
    private int answerPosition;
}
