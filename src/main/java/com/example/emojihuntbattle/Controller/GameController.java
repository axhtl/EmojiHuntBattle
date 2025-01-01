package com.example.emojihuntbattle.Controller;

import com.example.emojihuntbattle.Domain.Player;
import com.example.emojihuntbattle.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // 플레이어 번호로 점수를 +1 증가시키는 API
    @PostMapping("/players/{playerId}/score")
    public ResponseEntity<Player> increasePlayerScore(@PathVariable String playerId) {
        Player updatedPlayer = gameService.increasePlayerScore(playerId);
        return ResponseEntity.ok(updatedPlayer);
    }

    // 방 번호로 두 플레이어의 점수를 반환하는 API
    @GetMapping("/rooms/{roomId}/scores")
    public ResponseEntity<?> getRoomPlayersScores(@PathVariable Long roomId) {
        return ResponseEntity.ok(gameService.getRoomPlayersScores(roomId));
    }
}
