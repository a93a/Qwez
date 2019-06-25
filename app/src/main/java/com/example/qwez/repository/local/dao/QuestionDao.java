package com.example.qwez.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qwez.repository.local.entity.Question;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Dao for Question
 */
@Dao
public interface QuestionDao extends BaseDao<Question> {

    @Query("SELECT * FROM questions")
    Single<List<Question>> getAll();

    @Query("DELETE FROM questions WHERE question_id=:id")
    Completable deleteById(int id);

    @Update
    Single<Integer> insertAndReturnId(Question question);

    @Query("SELECT * FROM QUESTIONS where id=:id")
    Single<Question> getById(int id);

    @Query("SELECT * FROM QUESTIONS where question_id=:id")
    Flowable<List<Question>> getAllQuestionsByGameId(int id);

}
