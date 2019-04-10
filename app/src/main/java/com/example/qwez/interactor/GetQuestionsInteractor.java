package com.example.qwez.interactor;

import com.example.qwez.interactor.rx.QuestionConverterOperator;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.Question;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionConverter;
import com.example.qwez.util.QuestionType;

import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableConverter;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetQuestionsInteractor {

    private final OpenTDBType openTDBType;
    private final GameRepositoryType gameRepositoryType;

    public GetQuestionsInteractor(OpenTDBType openTDBType, GameRepositoryType gameRepositoryType) {
        this.openTDBType = openTDBType;
        this.gameRepositoryType = gameRepositoryType;
    }

    public Single<List<Question>> getQuestionByCategoryMultiple(Category category, Difficulty difficulty){

        //openTDBType.getQuestionByCategory(QuestionC.AMOUNT_STANDARD, category.getCategory(), difficulty.getDifficulty(), QuestionType.MULTIPLE_CHOICE.getType())
        //.map(QuestionConverter::toDatabase)
        //gameRepositoryType.addGame(new Game(Category.getAsString(category),difficulty.getDifficulty()))
        //.observeOn(AndroidSchedulers.mainThread());


        return openTDBType
                .getQuestionByCategory(QuestionC.AMOUNT_STANDARD,
                        category.getCategory(),
                        difficulty.getDifficulty(),
                        QuestionType.MULTIPLE_CHOICE.getType())
                .map(QuestionConverter::toDatabase)
                .flatMap(questions -> {
                    int id = gameRepositoryType.addGameReturnId(new Game(Category.getAsString(category),
                            difficulty.getDifficulty())).blockingGet().intValue();
                    questions.forEach(question -> question.setqId(id));
                    gameRepositoryType.addQuestions(questions);
                    return gameRepositoryType.getAllQuestions(); })
                .observeOn(AndroidSchedulers.mainThread());


        /*
        return openTDBType
                .getQuestionByCategory(QuestionC.AMOUNT_STANDARD,
                        category.getCategory(),
                        difficulty.getDifficulty(),
                        QuestionType.MULTIPLE_CHOICE.getType())
                .map(QuestionConverter::toDatabase)
                .flatMap(questions -> {
                    return gameRepositoryType.addGameReturnId(new Game(Category.getAsString(category),difficulty.getDifficulty()))
                    .map(id -> {
                        questions.forEach(question -> question.setqId((int)(long)id));  //interesting cast
                        return questions;
                    });   // returns Single<Long>
                }) // returns Single<List<Question>> with the GameId attached
                .flatMapCompletable(gameRepositoryType::addQuestions) // returns Completable
                .andThen(gameRepositoryType.getAllQuestions()) // returns Single<>
                .observeOn(AndroidSchedulers.mainThread());
        */

    }

}
