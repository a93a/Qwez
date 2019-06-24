package com.example.qwez.repository.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Game @Entity
 */
@Entity(tableName = "games")
public class Game {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int gameId;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "difficulty")
    private String difficulty;

    @ColumnInfo(name = "answered")
    private int answered;

    public Game(String category, String difficulty) {
        this.category = category;
        this.difficulty = difficulty;
        this.answered = 0;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }
}
