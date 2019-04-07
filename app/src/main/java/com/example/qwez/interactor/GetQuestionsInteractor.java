package com.example.qwez.interactor;

import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetQuestionsInteractor {

    private final OpenTDBType openTDBType;
    private final GameRepositoryType gameRepositoryType;

    public GetQuestionsInteractor(OpenTDBType openTDBType, GameRepositoryType gameRepositoryType) {
        this.openTDBType = openTDBType;
        this.gameRepositoryType = gameRepositoryType;
    }

    public Single<List<Question>> getQuestionByCategoryMultiple(Category category, Difficulty difficulty){
        return openTDBType
                .getQuestionByCategory(
                        QuestionC.AMOUNT_STANDARD,
                        category.getCategory(),
                        difficulty.getDifficulty(),
                        QuestionType.MULTIPLE_CHOICE.getType())
                .doOnSuccess(questions -> {
                    gameRepositoryType.addGame(new Game(
                            questions.get(0).getCategory(),
                            questions.get(0).getDifficulty())); })
                .observeOn(AndroidSchedulers.mainThread());
    }

}
