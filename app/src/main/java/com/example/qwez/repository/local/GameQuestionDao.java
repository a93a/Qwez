package com.example.qwez.repository.local;

import android.media.audiofx.BassBoost;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface GameQuestionDao {

    @Query("SELECT * FROM GAMES")
    Flowable<List<GameQuestion>> getGameQuestions();

}
