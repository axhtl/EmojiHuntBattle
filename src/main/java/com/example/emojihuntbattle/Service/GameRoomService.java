package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    // 방 목록을 관리하는 리스트 (현재 예시로 메모리 내에서 관리)
    private List<GameRoom> rooms = new ArrayList<>();

    public GameRoom findEmptyRoom() {
        // 빈 방을 찾는 로직
        for (GameRoom room : rooms) {
            if (room.getPlayers().size() < 2) {
                return room; // 플레이어가 2명 미만인 방을 반환
            }
        }
        return null; // 빈 방이 없으면 null 반환
    }

    public GameRoom createNewRoom() {
        // 새 방을 생성하는 로직
        GameRoom newRoom = new GameRoom();
        rooms.add(newRoom);
        return newRoom;
    }

    public void addPlayerToRoom(GameRoom room, String playerId) {
        room.addPlayer(playerId); // 방에 플레이어 추가
    }
}