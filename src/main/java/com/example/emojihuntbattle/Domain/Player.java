package com.example.emojihuntbattle.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")  // 외래 키로 roomId 사용
    @JsonBackReference
    private GameRoom gameRoom;  // 플레이어가 속한 방

    // 점수 증가 메서드
    public void increaseScore() {
        this.score++; // 점수 1 증가
    }

    // PlayerId 생성 로직
    public static Player createNewPlayer() {
        return new Player(UUID.randomUUID().toString(), 0, null); // UUID로 자동 생성된 ID
    }

    // gameRoom 설정 메서드 추가
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
}