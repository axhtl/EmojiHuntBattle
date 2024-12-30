package com.example.emojihuntbattle.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoundAnswer {
    private int round;
    private int boardSize;
    private int number;
}
