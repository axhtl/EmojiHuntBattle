package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Domain.Player;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import com.example.emojihuntbattle.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    // 빈 방을 찾는 로직
    public GameRoom findEmptyRoom() {
        return gameRoomRepository.findAll()
                .stream()
                .filter(room -> room.getPlayers().size() < 2)
                .findFirst()
                .orElse(null); // 빈 방이 없으면 null 반환
    }

    // 새 방을 생성하는 로직
    public GameRoom createNewRoom() {
        GameRoom newRoom = new GameRoom(); // 새 방 생성
        return gameRoomRepository.save(newRoom); // DB에 저장 후 반환
    }

    // 방에 플레이어 추가
    public void addPlayerToRoom(GameRoom room, Player player) {
        room.addPlayer(player);

        // 플레이어가 2명 되면 방이 준비됨
        if (room.getPlayers().size() == 2) {
            room.setRoomReady(true);
        }

        // 방 정보 저장
        gameRoomRepository.save(room);
    }
}