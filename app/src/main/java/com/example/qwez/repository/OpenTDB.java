package com.example.qwez.repository;

import com.example.qwez.repository.entity.Question;
import com.example.qwez.repository.entity.ResponseBody;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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
                        .getQuestions(amount, category, difficulty, type)
                        .lift(new ApiErrorOperator<>())
                        .map(ResponseBody::getQuestions)
                        .subscribeOn(Schedulers.io())
        );
    }

}
