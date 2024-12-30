package com.example.emojihuntbattle.Repository;

import com.example.emojihuntbattle.Domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Player findByPlayerId(String playerId);  // playerId로 플레이어를 찾는 메서드
}