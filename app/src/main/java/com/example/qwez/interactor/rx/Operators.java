package com.example.qwez.interactor.rx;

import com.example.qwez.entity.Highscore;
import com.example.qwez.repository.local.entity.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Rx Util Class with RxJava Operators & Transformations
 */
public class Operators {

    /**
     * Takes a Game object and increments its Answered field
     */
    public static final Function<Game,Game> GAME_MAPPER = game -> {
        int ans = game.getAnswered();
        ans++;
        game.setAnswered(ans);
        return game;
    };

    /**
     * Takes a List of Highscore Objects and sorts them in Descending order
     */
    public static final Function<List<Highscore>,List<Highscore>> SORT_HIGHSCORES = highscores -> {
        List<Highscore> sorted = new ArrayList<>(highscores);
        Collections.sort(sorted, (o1, o2) -> o2.getScore() - o1.getScore());
        return sorted;
    };

}
