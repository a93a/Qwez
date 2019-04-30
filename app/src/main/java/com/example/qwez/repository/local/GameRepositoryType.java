package com.example.qwez.repository.local;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Gamerepository interface
 */
public interface GameRepositoryType {

    /**
     * Get a Flowable that emits all Game(s) at subscribe(), and on each update in database
     */
    Flowable<List<Game>> getAllGames();

    /**
     * Add a game to database
     */
    Completable addGame(Game game);

    /**
     * Add a game to database, and get newly created games id
     */
    Single<Long> addGameReturnId(Game game);

    /**
     * Delete a game from database
     */
    Completable deleteGame(Game game);

    /**
     * Update a game in the database
     */
    Completable updateGame(Game game);

    /**
     * Get a Single that emits a list of all questions in the database
     */
    Single<List<Question>> getAllQuestions();

    /**
     * Add a question to the database
     */
    Completable addQuestion(Question question);

    /**
     * Add one or more questions to the database
     */
    Completable addQuestions(List<Question> questions);

    /**
     * Delete a question from the database
     */
    Completable deleteQuestion(Question question);

    /**
     * Update a question in the database
     */
    Completable updateQuestion(Question question);

    /**
     * Get a Flowable that emits all GameQuestion(s) at subscribe(), and on each update in database
     */
    Flowable<List<GameQuestion>> getAllGamesAndQuestions();

    /**
     * Get a Flowable that emits from a single GameQuestion
     */
    Flowable<GameQuestion> getGameQuestionBy(int id);

}
