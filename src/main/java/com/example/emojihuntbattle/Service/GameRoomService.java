package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import com.example.emojihuntbattle.WebSocket.GameWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final GameWebSocketHandler gameWebSocketHandler;

    // 방 준비 상태 변경 시 브로드캐스트 메시지 전송
    public void handleRoomReady(GameRoom gameRoom) {
        // 방이 준비된 상태일 때만 브로드캐스트
        if (gameRoom.isRoomReady()) {
            String message = "Room " + gameRoom.getRoomId() + " is ready! All players can start the game.";
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

    // 라운드 1의 정답을 브로드캐스트 메시지 전송
    public void handleRound1Answer(GameRoom gameRoom) {
        if (gameRoom.getRound1Answer()!=null) {
            String message = "방 번호: " + gameRoom.getRoomId() +"  Round1 정답 위치: " + gameRoom.getRound1Answer();
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

    public void handleRound2Answer(GameRoom gameRoom) {
        if (gameRoom.getRound2Answer()!=null) {
            String message = "방 번호: " + gameRoom.getRoomId() +"  Round2 정답 위치: " + gameRoom.getRound2Answer();
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

    public void handleRound3Answer(GameRoom gameRoom) {
        if (gameRoom.getRound3Answer()!=null) {
            String message = "방 번호: " + gameRoom.getRoomId() +"  Round3 정답 위치: " + gameRoom.getRound3Answer();
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

    public void handleRound4Answer(GameRoom gameRoom) {
        if (gameRoom.getRound4Answer()!=null) {
            String message = "방 번호: " + gameRoom.getRoomId() +"  Round4 정답 위치: " + gameRoom.getRound4Answer();
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

    public void handleRound5Answer(GameRoom gameRoom) {
        if (gameRoom.getRound5Answer()!=null) {
            String message = "방 번호: " + gameRoom.getRoomId() +"  Round5 정답 위치: " + gameRoom.getRound5Answer();
            gameWebSocketHandler.broadcastRoomReady(message); // 메시지 브로드캐스트
        }
    }

//    public Map<String, Object> getNextRoundInfo(Long roomId, int round) {
//        // 방 존재 여부 확인
//        Optional<GameRoom> gameRoomOptional = gameRoomRepository.findById(roomId);
//        if (gameRoomOptional.isEmpty()) {
//            throw new IllegalArgumentException("유효하지 않은 방 번호입니다.");
//        }
//
//        // 보드 크기와 정답 범위 설정
//        int boardSize;
//        int maxNumber;
//        switch (round) {
//            case 1:
//                boardSize = 2;
//                maxNumber = 4;
//                break;
//            case 2:
//                boardSize = 3;
//                maxNumber = 9;
//                break;
//            case 3:
//                boardSize = 4;
//                maxNumber = 16;
//                break;
//            case 4:
//            case 5:
//                boardSize = 5;
//                maxNumber = 25;
//                break;
//            default:
//                throw new IllegalArgumentException("지원하지 않는 라운드 번호입니다.");
//        }
//
//        // 정답 위치 랜덤 생성
//        Random random = new Random();
//        int correctAnswer = random.nextInt(maxNumber) + 1; // 1부터 maxNumber 사이의 랜덤 값
//
//        // 정답 위치 DB 저장 및 정답 위치 브로드캐스트
//        if(round==1){
//            GameRoom gameRoom = gameRoomOptional.get();
//            gameRoom.setRound1Answer(correctAnswer);
//            handleRound1Answer(gameRoom);
//        } else if(round==2){
//            GameRoom gameRoom = gameRoomOptional.get();
//            gameRoom.setRound2Answer(correctAnswer);
//            handleRound2Answer(gameRoom);
//        } else if(round==3){
//            GameRoom gameRoom = gameRoomOptional.get();
//            gameRoom.setRound3Answer(correctAnswer);
//            handleRound3Answer(gameRoom);
//        } else if(round==4){
//            GameRoom gameRoom = gameRoomOptional.get();
//            gameRoom.setRound4Answer(correctAnswer);
//            handleRound4Answer(gameRoom);
//        } else if(round==5){
//            GameRoom gameRoom = gameRoomOptional.get();
//            gameRoom.setRound5Answer(correctAnswer);
//            handleRound5Answer(gameRoom);
//        }
//
//        GameRoom gameRoom = gameRoomOptional.get();
//        gameRoomRepository.save(gameRoom);
//
//        // 결과 생성
//        Map<String, Object> response = new HashMap<>();
//        response.put("roomId", roomId);
//        response.put("round", round);
//        response.put("boardSize", boardSize);
//        response.put("answerNumber", correctAnswer);
//
//        return response;
//    }

    public Map<String, Object> getNextRoundInfo(Long roomId, int round) {
        // 방 존재 여부 확인
        Optional<GameRoom> gameRoomOptional = gameRoomRepository.findById(roomId);
        if (gameRoomOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 방 번호입니다.");
        }

        GameRoom gameRoom = gameRoomOptional.get();

        // 이미 저장된 정답 확인
        Integer existingAnswer = null;
        switch (round) {
            case 1:
                existingAnswer = gameRoom.getRound1Answer();
                break;
            case 2:
                existingAnswer = gameRoom.getRound2Answer();
                break;
            case 3:
                existingAnswer = gameRoom.getRound3Answer();
                break;
            case 4:
                existingAnswer = gameRoom.getRound4Answer();
                break;
            case 5:
                existingAnswer = gameRoom.getRound5Answer();
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 라운드 번호입니다.");
        }

        // 이미 정답이 존재하면 기존 정답 반환
        if (existingAnswer != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("round", round);
            response.put("boardSize", calculateBoardSize(round));
            response.put("answerNumber", existingAnswer);
            return response;
        }

        // 보드 크기와 정답 범위 설정
        int boardSize = calculateBoardSize(round);
        int maxNumber = boardSize * boardSize;

        // 정답 위치 랜덤 생성
        Random random = new Random();
        int correctAnswer = random.nextInt(maxNumber) + 1; // 1부터 maxNumber 사이의 랜덤 값

        // 정답 위치 DB 저장
        switch (round) {
            case 1:
                gameRoom.setRound1Answer(correctAnswer);
                handleRound1Answer(gameRoom);
                break;
            case 2:
                gameRoom.setRound2Answer(correctAnswer);
                handleRound2Answer(gameRoom);
                break;
            case 3:
                gameRoom.setRound3Answer(correctAnswer);
                handleRound3Answer(gameRoom);
                break;
            case 4:
                gameRoom.setRound4Answer(correctAnswer);
                handleRound4Answer(gameRoom);
                break;
            case 5:
                gameRoom.setRound5Answer(correctAnswer);
                handleRound5Answer(gameRoom);
                break;
        }

        gameRoomRepository.save(gameRoom);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("round", round);
        response.put("boardSize", boardSize);
        response.put("answerNumber", correctAnswer);

        return response;
    }

    private int calculateBoardSize(int round) {
        switch (round) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
            case 5:
                return 5;
            default:
                throw new IllegalArgumentException("지원하지 않는 라운드 번호입니다.");
        }
    }
}
