package com.example.qwez.repository.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Flowable;

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

}
