package com.example.qwez.repository.local;

import android.media.audiofx.BassBoost;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Flowable;

/**
 * Dao for GameQuestion objects
 */
@Dao
public interface GameQuestionDao {

    @Transaction
    @Query("SELECT * FROM GAMES")
    Flowable<List<GameQuestion>> getGameQuestions();

}
