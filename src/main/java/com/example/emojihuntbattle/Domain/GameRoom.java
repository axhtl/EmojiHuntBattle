package com.example.emojihuntbattle.Domain;

import com.example.emojihuntbattle.WebSocket.GameWebSocketHandler;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @JsonManagedReference // 순환 참조 방지
    private List<Player> players = new ArrayList<>(); // 플레이어 목록

    private boolean roomReady = false; // 방 준비 여부

    @Transient
    private GameWebSocketHandler webSocketHandler; // WebSocket 메시지를 전송하는 핸들러

    private Integer round1Answer;
    private Integer round2Answer;
    private Integer round3Answer;
    private Integer round4Answer;
    private Integer round5Answer;

    // 플레이어 추가
    public void addPlayer(Player player) {
        players.add(player);
        // 플레이어가 두 명이 되면 방 준비 완료
        if (players.size() == 2) {
            setRoomReady(true);  // 두 명이면 방 준비 완료 상태로 설정
        }
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

        // 방 준비 완료 시 WebSocket 메시지 전송
        if (roomReady && webSocketHandler != null) {
            // 준비 완료 메시지 생성
            String message = "Room " + this.roomId + " is ready!";
            webSocketHandler.broadcastRoomReady(message); // 생성한 메시지를 브로드캐스트
        }
    }

    public boolean isRoomReady() {
        return roomReady;
    }

    // 정답 저장 메서드
    public void setRound1Answer(Integer answer) {
        this.round1Answer = answer;
    }
    public void setRound2Answer(Integer answer) {
        this.round2Answer = answer;
    }
    public void setRound3Answer(Integer answer) {
        this.round3Answer = answer;
    }
    public void setRound4Answer(Integer answer) {
        this.round4Answer = answer;
    }
    public void setRound5Answer(Integer answer) {
        this.round5Answer = answer;
    }
}