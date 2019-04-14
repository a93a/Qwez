package com.example.qwez.repository.opentdb;

import com.example.qwez.repository.ApiErrorOperator;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.repository.opentdb.entity.ResponseBody;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class OpenTDB implements OpenTDBType {

    private final OpenTDBAPI api;

    public OpenTDB(OpenTDBAPI api) {
        this.api = api;
    }


    @Override
    public Single<List<Question>> getQuestionByCategory(int amount,
                                                        int category,
                                                        String difficulty,
                                                        String type) {
        return Single.fromObservable(
                api
                        .getQuestions(10, Category.FILMS.getCategory(), Difficulty.EASY.getDifficulty(), QuestionType.MULTIPLE_CHOICE.getType())
                        .lift(new ApiErrorOperator<>(   ))
                        .map(ResponseBody::getQuestions)
                        .subscribeOn(Schedulers.io())
        );
    }

}
