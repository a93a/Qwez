package com.example.qwez.interactor;

import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.GameQuestion;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

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
        return gameRepositoryType.getAllGamesAndQuestions()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
