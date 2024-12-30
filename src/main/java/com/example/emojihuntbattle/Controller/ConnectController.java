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
        Player newPlayer = Player.createNewPlayer(); // PlayerId는 자동 생성됨

        // 방에 플레이어 추가
        gameRoomService.addPlayerToRoom(gameRoom, newPlayer);

        return gameRoom;
    }
}