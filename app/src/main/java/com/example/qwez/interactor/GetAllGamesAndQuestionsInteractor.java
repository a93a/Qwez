package com.example.qwez.interactor;

import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.GameRepositoryType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetAllGamesAndQuestionsInteractor {

    private final GameRepositoryType gameRepositoryType;


    public GetAllGamesAndQuestionsInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    public Flowable<List<GameQuestion>> getAllGamesAndQuestions(){
        return gameRepositoryType
                .getAllGamesAndQuestions()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
