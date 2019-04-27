package com.example.qwez.interactor;

import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.GameRepositoryType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor for getting all games and questions
 */
public class GetAllGamesAndQuestionsInteractor {

    private final GameRepositoryType gameRepositoryType;

    public GetAllGamesAndQuestionsInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    /**
     * Get all Games and Questions continually.
     * @return Flowable that emitts GameQuestion object, once on subscribe, and there after emitts
     * on each change in database
     */
    public Flowable<List<GameQuestion>> getAllGamesAndQuestions(){
        return gameRepositoryType
                .getAllGamesAndQuestions()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
