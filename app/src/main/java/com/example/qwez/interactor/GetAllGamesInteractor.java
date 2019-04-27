package com.example.qwez.interactor;

import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to get all games.
 */
public class GetAllGamesInteractor {

    private final GameRepositoryType gameRepositoryType;

    public GetAllGamesInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    /**
     * Get all Games continually.
     * @return Flowable that emitts Game object, once on subscribe, and there after emitts
     * on each change in database
     */
    public Flowable<List<Game>> getAllGames(){
        return gameRepositoryType
                .getAllGames()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
