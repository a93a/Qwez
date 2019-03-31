package com.example.qwez.interactor;

import com.example.qwez.repository.OpenTDBType;
import com.example.qwez.repository.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetQuestionsInteractor {

    private final OpenTDBType openTDBType;

    public GetQuestionsInteractor(OpenTDBType openTDBType) {
        this.openTDBType = openTDBType;
    }

    public Single<List<Question>> getQuestionByCategoryMultiple(Category category, Difficulty difficulty){
        return openTDBType
                .getQuestionByCategory(
                        QuestionC.AMOUNT_STANDARD,
                        category.getCategory(),
                        difficulty.getDifficulty(),
                        QuestionType.MULTIPLE_CHOICE.getType()
                ).observeOn(AndroidSchedulers.mainThread());
    }

}
