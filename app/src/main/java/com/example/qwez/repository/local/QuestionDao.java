package com.example.qwez.repository.local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface QuestionDao extends BaseDao<Question> {

    @Query("SELECT * FROM questions")
    Single<List<Question>> getAll();

    @Query("DELETE FROM questions WHERE question_id=:id")
    Completable deleteById(int id);

}
