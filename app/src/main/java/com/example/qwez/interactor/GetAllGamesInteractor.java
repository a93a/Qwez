package com.example.qwez.interactor;

import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetAllGamesInteractor {

    private final GameRepositoryType gameRepositoryType;

    public GetAllGamesInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    public Flowable<List<Game>> getAllGames(){
        return gameRepositoryType
                .getAllGames()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
