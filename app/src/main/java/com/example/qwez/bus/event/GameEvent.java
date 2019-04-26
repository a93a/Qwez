package com.example.qwez.bus.event;

import com.example.qwez.repository.local.Game;

public class GameEvent {

    private final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
