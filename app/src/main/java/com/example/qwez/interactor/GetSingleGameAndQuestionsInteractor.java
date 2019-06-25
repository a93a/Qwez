package com.example.qwez.interactor;

import androidx.annotation.NonNull;

import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetSingleGameAndQuestionsInteractor {

    private final GameRepositoryType gameRepositoryType;

    public GetSingleGameAndQuestionsInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    /**
     * Get GameQuestion object with Game and all associated Question(s)
     * @param id of game
     * @return Flowable that emit first on subscribe(), and on each update in database, a GameQuestion object
     */
    public Flowable<GameQuestion> getGameQuestions(int id){
        return gameRepositoryType.getGameQuestionBy(id)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Question>> getAllGamesByQuestionID(int id){
        return gameRepositoryType.getAllQuestionsByGameId(id)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
