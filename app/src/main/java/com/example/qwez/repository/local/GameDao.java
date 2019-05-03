package com.example.qwez.repository.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

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

}
