package com.example.qwez.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.qwez.repository.local.entity.Game;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Dao for @Entity Game
 */
@Dao
public interface GameDao extends BaseDao<Game> {

    @Insert
    long insertReturnId(Game game);

    @Query("SELECT * FROM games")
    Flowable<List<Game>> getAll();

    @Query("DELETE FROM games WHERE id=:id")
    Completable deleteById(int id);

    @Query("SELECT * FROM games WHERE id=:id")
    Single<Game> getGameById(int id);

}
