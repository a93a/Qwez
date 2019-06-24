package com.example.qwez.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qwez.repository.local.dao.GameDao;
import com.example.qwez.repository.local.dao.GameQuestionDao;
import com.example.qwez.repository.local.dao.QuestionDao;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.Question;

/**
 * Application Database Class.
 *
 * Provides Dao(s)
 */
@Database(entities = {Game.class, Question.class}, version = 2)
public abstract class GameDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "game_database";

    public abstract GameDao gameDao();

    public abstract QuestionDao questionDao();

    public abstract GameQuestionDao gameQuestionDao();

}
