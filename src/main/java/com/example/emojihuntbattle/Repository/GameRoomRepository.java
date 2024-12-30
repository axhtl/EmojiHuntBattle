package com.example.emojihuntbattle.Repository;

import com.example.emojihuntbattle.Domain.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
}
