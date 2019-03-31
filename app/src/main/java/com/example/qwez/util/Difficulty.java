package com.example.qwez.util;

public enum  Difficulty {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String difficulty;

    Difficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
