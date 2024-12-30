package com.example.emojihuntbattle.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    private String playerId;  // 플레이어 ID
    private int score;  // 플레이어 점수

    // 점수 증가 메서드
    public void increaseScore() {
        this.score++; // 점수 1 증가
    }

    // PlayerId 생성 로직
    public static Player createNewPlayer() {
        return new Player(UUID.randomUUID().toString(), 0); // UUID로 자동 생성된 ID
    }
}