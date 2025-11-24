package com.example.repository;

import com.example.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    
    List<Game> findByStatus(Game.GameStatus status);
    List<Game> findByStatusIn(List<Game.GameStatus> statuses);
    @Query("SELECT g FROM Game g JOIN g.players p WHERE p.id = :playerId")
    List<Game> findGamesByPlayerId(@Param("playerId") String playerId);
    
    // Find games by current player
    List<Game> findByCurrentPlayerId(String currentPlayerId);
    
    // Find games by winner
    List<Game> findByWinnerId(String winnerId);
    
    // Find games created in date range
    List<Game> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find games updated in date range
    List<Game> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find games with most moves
    @Query("SELECT g FROM Game g ORDER BY SIZE(g.moves) DESC")
    List<Game> findGamesByMoveCount();
    
    List<Game> findByNameContainingIgnoreCase(String name);
    
    long countByStatus(Game.GameStatus status);
    
    List<Game> findTop10ByOrderByCreatedAtDesc();
    
    // Find games by player count
    @Query("SELECT g FROM Game g WHERE SIZE(g.players) = :playerCount")
    List<Game> findGamesByPlayerCount(@Param("playerCount") int playerCount);
    
    // Find games with specific player
    @Query("SELECT g FROM Game g JOIN g.players p WHERE p.id = :playerId AND g.status = :status")
    List<Game> findGamesByPlayerAndStatus(@Param("playerId") String playerId, @Param("status") Game.GameStatus status);

    default List<Game> findActiveGames() {
        return findByStatus(Game.GameStatus.ACTIVE);
    }

    default List<Game> findWaitingGames() {
        return findByStatus(Game.GameStatus.WAITING);
    }

    default List<Game> findCompletedGames() {
        return findByStatus(Game.GameStatus.COMPLETED);
    }

    default List<Game> findCompletedOrDrawGames() {
        return findByStatusIn(Arrays.asList(Game.GameStatus.COMPLETED, Game.GameStatus.DRAW));
    }

    default long countActiveGames() {
        return countByStatus(Game.GameStatus.ACTIVE);
    }

    default long countWaitingGames() {
        return countByStatus(Game.GameStatus.WAITING);
    }
}

// TODO: Add Player and Game model input validation [ttt.todo.model.validation]