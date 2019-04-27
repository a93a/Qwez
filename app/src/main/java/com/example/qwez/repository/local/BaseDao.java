package com.example.qwez.repository.local;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;

/**
 * Base Dao Class that all Dao(s) must implement
 */
public interface BaseDao<T> {

    /**
     * Insert T t into database
     * @param t object to insert
     * @return Completable that emits insertion result
     */
    @Insert
    Completable insert(T t);

    /**
     * Insert T ...t objects into database
     * @param t objects to insert
     * @return Completable that emits insertion result
     */
    @Insert
    Completable insert(T ...t);

    /**
     * Update T t object in the database
     * @param t object to update
     * @return Completable that emits update result
     */
    @Update
    Completable update(T t);

    /**
     * Delete T t object in the database
     * @param t object to delete
     * @return Completable that emits deletion result
     */
    @Delete
    Completable delete(T t);

}
