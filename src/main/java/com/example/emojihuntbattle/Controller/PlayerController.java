package com.example.emojihuntbattle.Controller;

import com.example.emojihuntbattle.DTO.PlayerRequest;
import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")  // "/players" 경로 설정
public class PlayerController {

    private final GameRoomService gameRoomService;

    @PostMapping("/connect")  // "/connect" 경로에 대한 POST 요청 처리
    public GameRoom connectPlayer(@RequestBody PlayerRequest playerRequest) {
        GameRoom gameRoom = gameRoomService.findEmptyRoom();

        if (gameRoom == null) {
            gameRoom = gameRoomService.createNewRoom();
        }

        gameRoomService.addPlayerToRoom(gameRoom, playerRequest.getPlayerId());

        return gameRoom;
    }
}