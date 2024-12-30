package com.example.emojihuntbattle.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId; // 방 번호 (자동 생성)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>(); // 플레이어 목록

    private boolean roomReady = false; // 방 준비 여부

    // 플레이어 추가
    public void addPlayer(Player player) {
        players.add(player);
    }

    // 플레이어 점수 증가
    public void increasePlayerScore(String playerId) {
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) {
                player.increaseScore(); // 점수 증가
                return;
            }
        }
    }

    // roomReady 상태 업데이트
    public void setRoomReady(boolean roomReady) {
        this.roomReady = roomReady;
    }
}