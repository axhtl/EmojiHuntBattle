package com.example.emojihuntbattle.Service;

import com.example.emojihuntbattle.Domain.GameRoom;
import com.example.emojihuntbattle.Domain.Player;
import com.example.emojihuntbattle.Repository.GameRoomRepository;
import com.example.emojihuntbattle.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;

    // 플레이어 번호로 점수를 +1 증가시키는 메서드
    public Player increasePlayerScore(String playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerId));
        player.increaseScore(); // 점수 증가
        return playerRepository.save(player); // 업데이트 후 반환
    }

    // 방 번호로 두 플레이어의 점수를 반환하는 메서드
    public Map<String, Integer> getRoomPlayersScores(Long roomId) {
        GameRoom gameRoom = gameRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방을 찾을 수 없습니다."));

        List<Player> players = gameRoom.getPlayers();
        if (players.size() != 2) {
            throw new IllegalStateException("방에 두 명의 플레이어가 없습니다.");
        }

        return players.stream()
                .collect(Collectors.toMap(Player::getPlayerId, Player::getScore));
    }
}
