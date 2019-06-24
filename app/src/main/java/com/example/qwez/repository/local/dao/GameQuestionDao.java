package com.example.qwez.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.qwez.repository.local.entity.GameQuestion;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Dao for GameQuestion objects
 */
@Dao
public interface GameQuestionDao {

    @Transaction
    @Query("SELECT * FROM GAMES")
    Flowable<List<GameQuestion>> getGameQuestions();

    @Transaction
    @Query("SELECT * FROM GAMES WHERE id=:id")
    Flowable<GameQuestion> getGameQuestionById(int id);

    /**
    @Transaction
    @Query("SELECT * FROM GAMES,QUESTIONS where GAMES.id=:gameId and QUESTIONS.answer_chosen=QUESTIONS.correct_answer")
    Single<Integer> getPoints(int gameId);
    **/

}
