package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Domain.RoundAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private List<GameRoom> rooms = new ArrayList<>();  // 방을 저장할 리스트

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
        room.addPlayer(playerId); // 방에 playerId 추가
    }

    // 라운드 번호에 따른 boardSize와 number 반환
    public RoundAnswer getNextRoundAnswer(String roomId, int round) {
        GameRoom room = findRoomById(roomId);

        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, roomId + " 는 존재하지 않는 방 번호입니다.");
        }

        int boardSize;
        int number;

        // boardSize와 number의 범위 결정
        if (round == 1) {
            boardSize = 2;
            number = (int) (Math.random() * 4) + 1;  // 1~4 범위
        } else if (round == 2) {
            boardSize = 3;
            number = (int) (Math.random() * 9) + 1;  // 1~9 범위
        } else if (round == 3) {
            boardSize = 4;
            number = (int) (Math.random() * 16) + 1; // 1~16 범위
        } else if (round == 4 || round == 5) {
            boardSize = 5;
            number = (int) (Math.random() * 25) + 1; // 1~25 범위
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 라운드 번호입니다: " + round);
        }

        return new RoundAnswer(round, boardSize, number);
    }

    private GameRoom findRoomById(String roomId) {
        for (GameRoom room : rooms) {
            if (room.getId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
}