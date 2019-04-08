package com.example.qwez.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Game.class, Question.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "game_database";

    public abstract GameDao gameDao();

    public abstract QuestionDao questionDao();

    public abstract GameQuestionDao gameQuestionDao();

}
