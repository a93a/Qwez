package com.example.qwez.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Dao for GameQuestion objects
 */
@Dao
public interface GameQuestionDao {

    @Transaction
    @Query("SELECT * FROM games")
    Flowable<List<GameQuestion>> getGameQuestions();

    @Transaction
    @Query("SELECT * FROM games WHERE id=:id")
    Flowable<GameQuestion> getGameQuestionById(int id);


    @Transaction
    @Query("SELECT * FROM questions where questions.answer_chosen=questions.correct_answer and questions.question_id=:gameId")
    Single<List<Question>> getPoints(int gameId);
    //can't manage to get it to return GameQuestion Object?

}
