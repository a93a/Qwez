package com.example.qwez.interactor;

import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionUtil;
import com.example.qwez.util.QuestionType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Interactor to get Questions from API, and store them in local database
 */
public class FetchQuestionsInteractor {

    private final OpenTDBType openTDBType;
    private final GameRepositoryType gameRepositoryType;

    public FetchQuestionsInteractor(OpenTDBType openTDBType, GameRepositoryType gameRepositoryType) {
        this.openTDBType = openTDBType;
        this.gameRepositoryType = gameRepositoryType;
    }

    /**
     * Get {@link com.example.qwez.repository.opentdb.entity.Question} Question from API,
     * convert it to an {@link com.example.qwez.repository.opentdb.entity.Question} object, and
     * store in the database.
     * @param category Question category
     * @param difficulty Question difficulty
     * @return Single that emitts a List of all Question objects in the local database, including the newly
     * returned Question from API
     */
    public Completable getQuestionByCategoryMultiple(Category category, Difficulty difficulty){
        return openTDBType.getQuestionByCategory(QuestionC.AMOUNT_STANDARD,
                        category.getCategory(),
                        difficulty.getDifficulty(),
                        QuestionType.MULTIPLE_CHOICE.getType())
                .map(QuestionUtil::toDatabase)
                .flatMap(questions -> gameRepositoryType.addGameReturnId(new Game(Category.getAsString(category),Difficulty.getAsString(difficulty)))
                        .map(aLong -> {
                            questions.forEach(question -> question.setqId((int) (long)aLong));
                            return questions;
                        }))
                .flatMapCompletable(gameRepositoryType::addQuestions)
                .observeOn(AndroidSchedulers.mainThread());

    }

}
