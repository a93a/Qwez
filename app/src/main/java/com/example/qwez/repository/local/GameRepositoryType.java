package com.example.qwez.repository.local;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface GameRepositoryType {

    Flowable<List<Game>> getAllGames();

    Completable addGame(Game game);

    Single<Long> addGameReturnId(Game game);

    Completable deleteGame(Game game);

    Completable updateGame(Game game);

    Single<List<Question>> getAllQuestions();

    Completable addQuestion(Question question);

    Completable addQuestions(List<Question> questions);

    Completable deleteQuestion(Question question);

    Completable updateQuestion(Question question);

    Flowable<List<GameQuestion>> getAllGamesAndQuestions();

}
