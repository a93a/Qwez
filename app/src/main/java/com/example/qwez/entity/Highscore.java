package com.example.qwez.entity;

import java.io.Serializable;

public class Highscore implements Serializable{

    private String nick;
    private int score;

    public Highscore(){}

    public Highscore(String nick, int score) {
        this.nick = nick;
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public int getScore() {
        return score;
    }
}
