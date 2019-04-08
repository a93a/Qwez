package com.example.qwez.di;

import android.content.Context;

import com.example.qwez.repository.local.GameDao;
import com.example.qwez.repository.local.GameDatabase;
import com.example.qwez.repository.local.GameQuestionDao;
import com.example.qwez.repository.local.GameRepository;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.Question;
import com.example.qwez.repository.local.QuestionDao;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class LocalDatabaseModule {

    @Provides
    @ApplicationScope
    GameRepositoryType gameRepositoryType(GameDao gameDao, QuestionDao questionDao, GameQuestionDao gameQuestionDao){
        return new GameRepository(gameDao, questionDao,gameQuestionDao);
    }

    @Provides
    @ApplicationScope
    GameQuestionDao gameQuestionDao(GameDatabase gameDatabase){
        return gameDatabase.gameQuestionDao();
    }

    @Provides
    @ApplicationScope
    QuestionDao questionDao(GameDatabase gameDatabase){
        return gameDatabase.questionDao();
    }

    @Provides
    @ApplicationScope
    GameDao gameDao(GameDatabase gameDatabase){
        return gameDatabase.gameDao();
    }

    @Provides
    @ApplicationScope
    GameDatabase gameDatabase(Context context){
        return Room
                .databaseBuilder(context, GameDatabase.class, GameDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

}
