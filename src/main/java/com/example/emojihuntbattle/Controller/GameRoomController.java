package com.example.emojihuntbattle.Controller;

import com.example.emojihuntbattle.Service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GameRoomController {

    private final GameRoomService gameRoomService;

    @GetMapping("/rooms/{roomId}/next-round")
    public ResponseEntity<Map<String, Object>> getNextRound(
            @PathVariable Long roomId,
            @RequestParam int round
    ) {
        try {
            Map<String, Object> roundInfo = gameRoomService.getNextRoundInfo(roomId, round);
            return ResponseEntity.ok(roundInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}