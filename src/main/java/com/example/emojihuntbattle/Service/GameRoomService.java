package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import com.example.emojihuntbattle.WebSocket.GameWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Lint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final GameWebSocketHandler gameWebSocketHandler;
    private final ApplicationEventPublisher eventPublisher;

    // 방 준비 상태 변경 시 브로드캐스트 메시지 전송
    public void handleRoomReady(GameRoom gameRoom) {
        // 방이 준비된 상태일 때만 브로드캐스트
        if (gameRoom.isRoomReady()) {
            String message = "Room " + gameRoom.getRoomId() + " is ready!";
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

    @Transactional
    public void broadAnswerAfter3seconds(GameRoom gameRoom, int startingRound) {
        RestTemplate restTemplate = new RestTemplate();
        Long roomId = gameRoom.getRoomId();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 각 라운드별 랜덤 정답 위치 지정
        int answerPosition = getRandomAnswerPosition(startingRound);

        scheduler.scheduleAtFixedRate(new Runnable() {
            private int currentRound = startingRound;

            @Override
            public void run() {
                if (currentRound > 5) {
                    scheduler.shutdown();
                    return;
                }

                try {
                    switch (currentRound) {
                        case 1 -> {
                            int answerPosition = getRandomAnswerPosition(currentRound);
                            Long gameRoomId = gameRoom.getRoomId();
                            int round = currentRound;
                            gameWebSocketHandler.broadcastAnswerPosition(gameRoomId,round,answerPosition);
                        }
                        case 2 -> {
                            int answerPosition = getRandomAnswerPosition(currentRound);
                            Long gameRoomId = gameRoom.getRoomId();
                            int round = currentRound;
                            gameWebSocketHandler.broadcastAnswerPosition(gameRoomId,round,answerPosition);
                        }
                        case 3 -> {
                            int answerPosition = getRandomAnswerPosition(currentRound);
                            Long gameRoomId = gameRoom.getRoomId();
                            int round = currentRound;
                            gameWebSocketHandler.broadcastAnswerPosition(gameRoomId,round,answerPosition);
                        }
                        case 4 -> {
                            int answerPosition = getRandomAnswerPosition(currentRound);
                            Long gameRoomId = gameRoom.getRoomId();
                            int round = currentRound;
                            gameWebSocketHandler.broadcastAnswerPosition(gameRoomId,round,answerPosition);
                        }
                        case 5 -> {
                            int answerPosition = getRandomAnswerPosition(currentRound);
                            Long gameRoomId = gameRoom.getRoomId();
                            int round = currentRound;
                            gameWebSocketHandler.broadcastAnswerPosition(gameRoomId,round,answerPosition);
                        }
                    }

                    currentRound++;
                } catch (Exception e) {
                    e.printStackTrace();
                    scheduler.shutdown(); // 오류 발생 시 스케줄러 종료
                }
            }
        }, 0, 3, TimeUnit.SECONDS); // 0초 후 시작, 이후 3초 간격으로 실행
    }

    // 라운드별 랜덤 정답 위치를 결정하는 메서드
    private int getRandomAnswerPosition(int round) {
        Random random = new Random();
        int answerPosition = 0;

        switch (round) {
            case 1:
                answerPosition = random.nextInt(4) + 1; // 1 ~ 4 범위
                break;
            case 2:
                answerPosition = random.nextInt(9) + 1; // 1 ~ 9 범위
                break;
            case 3:
                answerPosition = random.nextInt(16) + 1; // 1 ~ 16 범위
                break;
            case 4:
            case 5:
                answerPosition = random.nextInt(25) + 1; // 1 ~ 25 범위
                break;
        }

        return answerPosition;
    }






//    public void broadAnswerAfter3seconds(GameRoom gameRoom, int startingRound) {
//        RestTemplate restTemplate = new RestTemplate();
//        Long roomId = gameRoom.getRoomId();
//
//        // 새로운 스레드에서 순차적으로 라운드 진행
//        new Thread(() -> {
//            try {
//                for (int round = startingRound; round <= 5; round++) {
//                    switch (round) {
//                        case 1 -> {
//                            callNextRoundApi(restTemplate, roomId, 1);
//                            Thread.sleep(10000);
//                            handleRound1Answer(gameRoom);
//                        }
//                        case 2 -> {
//                            callNextRoundApi(restTemplate, roomId, 2);
//                            Thread.sleep(10000);
//                            handleRound2Answer(gameRoom);
//                        }
//                        case 3 -> {
//                            callNextRoundApi(restTemplate, roomId, 3);
//                            Thread.sleep(10000);
//                            handleRound3Answer(gameRoom);
//                        }
//                        case 4 -> {
//                            callNextRoundApi(restTemplate, roomId, 4);
//                            Thread.sleep(10000);
//                            handleRound4Answer(gameRoom);
//                        }
//                        case 5 -> {
//                            callNextRoundApi(restTemplate, roomId, 5);
//                            Thread.sleep(10000);
//                            handleRound5Answer(gameRoom);
//                        }
//                    }
//
//                    // 3초 대기
//                    if (round < 5) {
//                        Thread.sleep(3000);
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

    private void callNextRoundApi(RestTemplate restTemplate, Long roomId, int round) {
        try {
            String apiUrl = "http://localhost:8080/rooms/" + roomId + "/next-round?round=" + round;
            ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

            // API 호출 결과 로그 출력
            System.out.println("라운드 " + round + " API 호출 성공: " + response.getBody());
        } catch (Exception e) {
            System.err.println("라운드 " + round + " API 호출 실패: " + e.getMessage());
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
//                handleRound1Answer(gameRoom);
                break;
            case 2:
                gameRoom.setRound2Answer(correctAnswer);
//                handleRound2Answer(gameRoom);
                break;
            case 3:
                gameRoom.setRound3Answer(correctAnswer);
//                handleRound3Answer(gameRoom);
                break;
            case 4:
                gameRoom.setRound4Answer(correctAnswer);
//                handleRound4Answer(gameRoom);
                break;
            case 5:
                gameRoom.setRound5Answer(correctAnswer);
//                handleRound5Answer(gameRoom);
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
