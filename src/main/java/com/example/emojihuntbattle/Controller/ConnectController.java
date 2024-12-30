package com.example.emojihuntbattle.Controller;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Domain.Player;
import com.example.emojihuntbattle.Service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConnectController {

    private final GameRoomService gameRoomService;

    @PostMapping("/connect")
    public GameRoom connectPlayer() {

        // 빈 방 찾기
        GameRoom gameRoom = gameRoomService.findEmptyRoom();

        // 빈 방이 없으면 새 방 생성
        if (gameRoom == null) {
            gameRoom = gameRoomService.createNewRoom();
        }

        // 새로운 플레이어 생성 (PlayerId는 자동으로 생성됨)
        Player newPlayer = Player.createNewPlayer(); // Player 생성

        // 방에 플레이어 추가 전에, 게임룸을 설정
        newPlayer.setGameRoom(gameRoom); // 새로운 플레이어의 gameRoom 설정

        // 방에 플레이어 추가
        gameRoom.addPlayer(newPlayer);

        // gameRoom을 저장하여 DB에 반영 (추가)
        gameRoomService.saveGameRoom(gameRoom);

        return gameRoom;
    }
}