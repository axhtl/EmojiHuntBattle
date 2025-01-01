package com.example.emojihuntbattle.Controller;
//
//import com.example.emojihuntbattle.Domain.RoundAnswer;
//import com.example.emojihuntbattle.Service.GameRoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class GameRoomController {
//    private final GameRoomService gameRoomService;
//
//    // GET /rooms/{roomId}/next-round?round={round}
//    @GetMapping("/rooms/{roomId}/next-round")
//    public ResponseEntity<RoundAnswer> getNextRoundAnswer(
//            @PathVariable String roomId,
//            @RequestParam int round) {
//
//        try {
//            RoundAnswer roundAnswer = gameRoomService.getNextRoundAnswer(roomId, round);
//            return new ResponseEntity<>(roundAnswer, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // 잘못된 요청 처리
//        }
//    }
//}

import com.example.emojihuntbattle.Service.ConnectService;
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