package com.example.qwez.repository.local;

import com.example.qwez.repository.local.dao.GameDao;
import com.example.qwez.repository.local.dao.GameQuestionDao;
import com.example.qwez.repository.local.dao.QuestionDao;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Game database repository
 */
public class GameRepository implements GameRepositoryType {

    private final GameDao gameDao;
    private final QuestionDao questionDao;
    private final GameQuestionDao gameQuestionDao;
    private final GameDatabase gameDatabase;

    @Inject
    public GameRepository(GameDao gameDao,
                          QuestionDao questionDao,
                          GameQuestionDao gameQuestionDao,
                          GameDatabase gameDatabase) {
        this.gameDao = gameDao;
        this.questionDao = questionDao;
        this.gameQuestionDao = gameQuestionDao;
        this.gameDatabase = gameDatabase;
    }

    /**
     * Get a Flowable that emits all Game(s) at subscribe(), and on each update in database
     * @return Flowable that emits List of Game objects, once at subscribe() and then after on each database update
     */
    @Override
    public Flowable<List<Game>> getAllGames() {
        return gameDao
                .getAll()
                .subscribeOn(Schedulers.io());
    }

    /**
     * Add a game to database
     * @param game to add
     * @return Completable that emits operation result
     */
    @Override
    public Completable addGame(Game game) {
        return gameDao
                .insert(game)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Add a game to database, and get newly created games id
     * @param game to add
     * @return Single that emits added Game id
     */
    @Override
    public Single<Long> addGameReturnId(Game game) {
        return Single
                .just(gameDao.insertReturnId(game))
                .subscribeOn(Schedulers.io());
    }

    /**
     * Delete a game from database
     * @param game to delete
     * @return Completable that emits operation result
     */
    @Override
    public Completable deleteGame(Game game) {
        return gameDao
                .delete(game)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Update a game in the database
     * @param game to update
     * @return Completable that emits operation result
     */
    @Override
    public Completable updateGame(Game game) {
        return gameDao
                .update(game)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Get a Single that emits a list of all questions in the database
     * @return Single that emits List of all Question in database
     */
    @Override
    public Single<List<Question>> getAllQuestions() {
        return questionDao
                .getAll()
                .subscribeOn(Schedulers.io());
    }

    /**
     * Add a question to the database
     * @param question to add
     * @return Completable that emits operation result
     */
    @Override
    public Completable addQuestion(Question question) {
        return questionDao
                .insert(question)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Add one or more questions to the database
     * @param questions Question(s) to add
     * @return Completable that emits operation result
     */
    @Override
    public Completable addQuestions(List<Question> questions) {
        return questionDao
                .insert(questions.toArray(new Question[questions.size()]))
                .subscribeOn(Schedulers.io());
    }

    /**
     * Delete a question from the database
     * @param question to delete
     * @return Completable that emits operation result
     */
    @Override
    public Completable deleteQuestion(Question question) {
        return questionDao
                .delete(question)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Update a question in the database
     * @param question to update
     * @return Completable that emits operation result
     */
    @Override
    public Completable updateQuestion(Question question) {
        return questionDao
                .update(question)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Get a Flowable that emits all GameQuestion(s) at subscribe(), and on each update in database
     * @return Flowable that emits all GameQuestion(s) at subscribe(), and on each update in database
     */
    @Override
    public Flowable<List<GameQuestion>> getAllGamesAndQuestions() {
        return gameQuestionDao
                .getGameQuestions()
                .subscribeOn(Schedulers.io());
    }

    /**
     * * Get a Flowable that emits from a single GameQuestion
     * @param id of game
     * @return Flowable that emits GameQuestion object
     */
    @Override
    public Flowable<GameQuestion> getGameQuestionBy(int id) {
        return gameQuestionDao.getGameQuestionById(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Integer> updateQuestionAndReturnId(Question question) {
        return questionDao.insertAndReturnId(question)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Question> getSingleQuestionById(int id) {
        return questionDao.getById(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Game> getGameById(int id) {
        return gameDao.getGameById(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromRunnable(gameDatabase::clearAllTables)
                .subscribeOn(Schedulers.io());
    }

}
