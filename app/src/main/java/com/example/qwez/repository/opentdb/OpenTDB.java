package com.example.qwez.repository.opentdb;

import com.example.qwez.repository.ApiOperator;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.repository.opentdb.entity.ResponseBody;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * OPENTDB API Repository
 */
public class OpenTDB implements OpenTDBType {

    private final OpenTDBAPI api;

    public OpenTDB(OpenTDBAPI api) {
        this.api = api;
    }


    /**
     * Get List of Question(s)
     * @param amount of questions
     * @param category Question category
     * @param difficulty Question difficulty
     * @param type Question type
     * @return Single that emits List of Question
     */
    @Override
    public Single<List<Question>> getQuestionByCategory(int amount,
                                                        int category,
                                                        String difficulty,
                                                        String type) {
        return Single.fromObservable(
                api
                        .getQuestions(10, category, difficulty, QuestionType.MULTIPLE_CHOICE.getType())
                        .lift(new ApiOperator<>())
                        .map(ResponseBody::getQuestions)
                        .subscribeOn(Schedulers.io())
        );
    }

}
