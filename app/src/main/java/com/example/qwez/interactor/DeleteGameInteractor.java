package com.example.qwez.interactor;

import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Game;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor for deleting games.
 */
public class DeleteGameInteractor {

    private final GameRepositoryType gameRepositoryType;

    public DeleteGameInteractor(GameRepositoryType gameRepositoryType){
        this.gameRepositoryType = gameRepositoryType;
    }

    /**
     * Delete {@code game} from local database
     * @param game @Entity object to delete
     * @return Completable
     */
    public Completable deleteGame(Game game){
        return gameRepositoryType.deleteGame(game)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
