package com.example.qwez.repository.local;

import android.media.audiofx.BassBoost;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface GameQuestionDao extends BaseDao<GameQuestion> {

    @Query("SELECT * FROM GAMES")
    Flowable<GameQuestion> getGameQuestions();

}
