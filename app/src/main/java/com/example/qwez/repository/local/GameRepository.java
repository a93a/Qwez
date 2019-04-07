package com.example.qwez.repository.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameRepository implements GameRepositoryType {

    private GameDao gameDao;
    private QuestionDao questionDao;

    @Inject
    public GameRepository(GameDao gameDao, QuestionDao questionDao) {
        this.gameDao = gameDao;
        this.questionDao = questionDao;
    }

    @Override
    public Flowable<List<Game>> getAllGames() {
        return gameDao
                .getAll()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable addGame(Game game) {
        return gameDao
                .insert(game)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteGame(Game game) {
        return gameDao
                .delete(game)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateGame(Game game) {
        return gameDao
                .update(game)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Question>> getAllQuestions() {
        return questionDao
                .getAll()
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Completable addQuestion(Question question) {
        return questionDao
                .insert(question)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteQuestion(Question question) {
        return questionDao
                .delete(question)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateQuestion(Question question) {
        return questionDao
                .update(question)
                .subscribeOn(Schedulers.io());
    }

}
