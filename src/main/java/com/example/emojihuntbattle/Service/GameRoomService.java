package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
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
//    private final GameRoomService gameRoomService;

    public Map<String, Object> getNextRoundInfo(Long roomId, int round) {
        // 방 존재 여부 확인
        Optional<GameRoom> gameRoomOptional = gameRoomRepository.findById(roomId);
        if (gameRoomOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 방 번호입니다.");
        }

        // 보드 크기와 정답 범위 설정
        int boardSize;
        int maxNumber;
        switch (round) {
            case 1:
                boardSize = 2;
                maxNumber = 4;
                break;
            case 2:
                boardSize = 3;
                maxNumber = 9;
                break;
            case 3:
                boardSize = 4;
                maxNumber = 16;
                break;
            case 4:
            case 5:
                boardSize = 5;
                maxNumber = 25;
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 라운드 번호입니다.");
        }

        // 정답 위치 랜덤 생성
        Random random = new Random();
        int correctAnswer = random.nextInt(maxNumber) + 1; // 1부터 maxNumber 사이의 랜덤 값

        // 정답 위치 DB 저장
        if(round==1){
            GameRoom gameRoom = gameRoomOptional.get();
            gameRoom.setRound1Answer(correctAnswer);
        } else if(round==2){
            GameRoom gameRoom = gameRoomOptional.get();
            gameRoom.setRound2Answer(correctAnswer);
        } else if(round==3){
            GameRoom gameRoom = gameRoomOptional.get();
            gameRoom.setRound3Answer(correctAnswer);
        } else if(round==4){
            GameRoom gameRoom = gameRoomOptional.get();
            gameRoom.setRound4Answer(correctAnswer);
        } else if(round==5){
            GameRoom gameRoom = gameRoomOptional.get();
            gameRoom.setRound5Answer(correctAnswer);
        }

        GameRoom gameRoom = gameRoomOptional.get();
        gameRoomRepository.save(gameRoom);

        // 결과 생성
        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("round", round);
        response.put("boardSize", boardSize);
        response.put("answerNumber", correctAnswer);

        return response;
    }
}
