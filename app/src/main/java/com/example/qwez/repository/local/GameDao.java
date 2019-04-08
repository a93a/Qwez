package com.example.qwez.repository.local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface GameDao extends BaseDao<Game> {

    @Insert
    long insertReturnId(Game game);

    @Query("SELECT * FROM games")
    Flowable<List<Game>> getAll();

    @Query("DELETE FROM games WHERE id=:id")
    Completable deleteById(int id);

}
