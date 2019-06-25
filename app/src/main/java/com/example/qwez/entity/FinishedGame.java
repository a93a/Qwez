package com.example.qwez.entity;

public class FinishedGame {

    private final int score;
    private final int gameID;

    public FinishedGame(int score, int gameID) {
        this.score = score;
        this.gameID = gameID;
    }

    public int getScore() {
        return score;
    }

    public int getGameID() {
        return gameID;
    }
}
