package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // GameRoom을 DB에 저장하는 메서드
    public GameRoom saveGameRoom(GameRoom gameRoom) {
        return gameRoomRepository.save(gameRoom);
    }
}