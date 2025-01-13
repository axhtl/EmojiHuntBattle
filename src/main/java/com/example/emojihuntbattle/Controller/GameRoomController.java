package com.example.emojihuntbattle.Controller;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import com.example.emojihuntbattle.Service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GameRoomController {

    private final GameRoomService gameRoomService;
    private final GameRoomRepository gameRoomRepository;

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

    @PostMapping("broad-answer/{roomId}")
    public void broadAnswer(@PathVariable Long roomId, @RequestParam int round) {
        GameRoom gameRoom = gameRoomRepository.findById(roomId).get();
        if(round==1){
            gameRoomService.handleRound1Answer(gameRoom);
        } else if(round==2){
            gameRoomService.handleRound2Answer(gameRoom);
        } else if(round==3){
            gameRoomService.handleRound3Answer(gameRoom);
        } else if(round==4){
            gameRoomService.handleRound4Answer(gameRoom);
        } else if(round==5){
            gameRoomService.handleRound5Answer(gameRoom);
        }
    }

    @PostMapping("broad-answer-after-3seconds/{roomId}")
    public void broadAnswerAfter3seconds(@PathVariable Long roomId, @RequestParam int round) {
        GameRoom gameRoom = gameRoomRepository.findById(roomId).get();
        gameRoomService.broadAnswerAfter3seconds(gameRoom,round);
    }
}