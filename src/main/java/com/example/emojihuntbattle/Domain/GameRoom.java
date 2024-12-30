package com.example.emojihuntbattle.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class GameRoom {

    private String id;  // 방 고유 ID
    private List<String> players;  // 방에 참가한 플레이어 리스트

    public GameRoom() {
        this.id = "room" + System.currentTimeMillis();  // 방 ID는 현재 시간을 기반으로 생성
        this.players = new ArrayList<>();
    }

    // 방에 플레이어 추가
    public void addPlayer(String playerId) {
        players.add(playerId);  // 플레이어 이름만 추가
    }

    // 방에 플레이어가 2명이 되면 게임 시작 준비 완료
    public boolean isRoomReady() {
        return players.size() >= 2;  // 플레이어가 2명이 되면 게임 준비 완료
    }
}