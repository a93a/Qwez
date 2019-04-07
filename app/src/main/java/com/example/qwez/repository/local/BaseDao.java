package com.example.qwez.repository.local;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;

public interface BaseDao<T> {

    @Insert
    Completable insert(T t);

    @Update
    Completable update(T t);

    @Delete
    Completable delete(T t);

}
