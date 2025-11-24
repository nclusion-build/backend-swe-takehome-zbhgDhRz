package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDateTime;

@Entity
@Table(name = "moves")
public class Move {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @NotNull(message = "Move position is required")
    @Min(value = 0, message = "Position must be between 0 and 8")
    @Max(value = 8, message = "Position must be between 0 and 8")
    @Column(nullable = false)
    private Integer position;
    
    @Column(nullable = false)
    private String symbol; // "X" or "O"
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    // Default constructor
    public Move() {
        this.timestamp = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public Move(Game game, Player player, Integer position, String symbol) {
        this();
        this.game = game;
        this.player = player;
        this.position = position;
        this.symbol = symbol;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Integer getPosition() {
        return position;
    }
    
    public void setPosition(Integer position) {
        this.position = position;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
