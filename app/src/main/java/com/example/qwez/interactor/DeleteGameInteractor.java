package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DeleteGameInteractor {

    private final GameRepositoryType gameRepositoryType;

    public DeleteGameInteractor(GameRepositoryType gameRepositoryType){
        this.gameRepositoryType = gameRepositoryType;
    }

    public Completable deleteGame(Game game){
        return gameRepositoryType.deleteGame(game)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
