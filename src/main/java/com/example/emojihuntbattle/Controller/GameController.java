//package com.example.emojihuntbattle.Controller;
//
//import com.example.emojihuntbattle.Domain.GameRoom;
//import com.example.emojihuntbattle.Domain.Player;
//import com.example.emojihuntbattle.Service.GameRoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class GameController {
//
//    private final GameRoomService gameRoomService;
//
//    // 정답을 제출한 후 점수 증가
//    @PostMapping("/submit-answer")
//    public ResponseEntity<Player> submitAnswer(@RequestBody String playerId) {
//        Player updatedPlayer = gameRoomService.increasePlayerScore(playerId); // 점수 증가
//        if (updatedPlayer != null) {
//            return ResponseEntity.ok(updatedPlayer); // 점수 증가된 플레이어 반환
//        }
//        return ResponseEntity.badRequest().build(); // 플레이어나 방을 찾을 수 없는 경우
//    }
//}