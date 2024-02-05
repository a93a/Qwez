package com.example.qwez.repository.local;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qwez.repository.local.dao.GameDao;
import com.example.qwez.repository.local.dao.GameQuestionDao;
import com.example.qwez.repository.local.dao.QuestionDao;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GameQuestionDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameDatabase gameDatabase;
    private GameQuestionDao dao;
    private GameDao gameDao;
    private QuestionDao questionDao;

    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();

        gameDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        dao = gameDatabase.gameQuestionDao();
        gameDao = gameDatabase.gameDao();
        questionDao = gameDatabase.questionDao();

    }

    @After
    public void closeDb() throws IOException {

        gameDatabase.close();

    }

    /*@Test
    public void storeAndGet() {
        Game game = new Game("cat","diff", answered);
        int id = (int) gameDao.insertReturnId(game);
        for(int i=0;i<5;i++){
            Question question = new Question("1", "2", "3", "4", "5");
            question.setqId(id);
            questionDao.insert(question).blockingAwait();
        }
        GameQuestion gameQuestion = dao.getGameQuestionById(id).blockingFirst();
        assertEquals(5, gameQuestion.questions.size());
    }*/
}