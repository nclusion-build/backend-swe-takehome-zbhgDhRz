package com.example.repository;

import com.example.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, String> {
    
    // Find moves by game ID
    List<Move> findByGameIdOrderByTimestampAsc(String gameId);
    
    // Find moves by player ID
    List<Move> findByPlayerId(String playerId);
    
    // Find moves by game and player
    List<Move> findByGameIdAndPlayerId(String gameId, String playerId);
    
    // Find moves in time range
    List<Move> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    // Find moves by position
    List<Move> findByPosition(int position);
    
    // Find moves by symbol (X or O)
    List<Move> findBySymbol(String symbol);
    
    // Count moves by game
    long countByGameId(String gameId);
    
    // Count moves by player
    long countByPlayerId(String playerId);
    
    // Find last move in a game
    @Query("SELECT m FROM Move m WHERE m.game.id = :gameId ORDER BY m.timestamp DESC")
    List<Move> findLastMoveByGame(@Param("gameId") String gameId);
    
    // Find moves by game and position
    List<Move> findByGameIdAndPosition(String gameId, int position);
    
    // Find moves by game and symbol
    List<Move> findByGameIdAndSymbol(String gameId, String symbol);
    
    // Find moves by player in time range
    @Query("SELECT m FROM Move m WHERE m.player.id = :playerId AND m.timestamp BETWEEN :startTime AND :endTime")
    List<Move> findMovesByPlayerInTimeRange(@Param("playerId") String playerId, 
                                           @Param("startTime") LocalDateTime startTime, 
                                           @Param("endTime") LocalDateTime endTime);
    
    // Find fastest moves (moves made quickly after game start)
    @Query("SELECT m FROM Move m WHERE m.game.id = :gameId AND m.timestamp <= :timeThreshold ORDER BY m.timestamp ASC")
    List<Move> findFastMoves(@Param("gameId") String gameId, @Param("timeThreshold") LocalDateTime timeThreshold);
    
    // Find moves by game status
    @Query("SELECT m FROM Move m WHERE m.game.status = :status")
    List<Move> findMovesByGameStatus(@Param("status") String status);
}
