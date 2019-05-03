package com.example.qwez.repository.local;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GameDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameDatabase gameDatabase;
    private GameDao gameDao;
    private final Game game = new Game("blah", "nah");



    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();

        gameDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        gameDao = gameDatabase.gameDao();

    }


    @After
    public void closeDb() throws IOException {

        gameDatabase.close();

    }

    @Test
    public void getAllEmpty(){

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==0);
    }

    @Test
    public void addGame(){

        gameDao
                .insert(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==1);

    }

    public void addGameSeveral(){

        gameDao
                .insert(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .insert(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==2);

    }

    @Test
    public void testDelete(){

        gameDao
                .insert(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==1);

        Game toDelete = gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .get(0);

        gameDao
                .delete(toDelete)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==0);

    }

    @Test
    public void test(){

        Game localMutableGame = new Game("123", "456");

        gameDao
                .insert(localMutableGame)
                .test()
                .assertNoErrors()
                .assertComplete();

        Game toUpdate = gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .get(0);

        toUpdate.setCategory("789");

        gameDao
                .update(toUpdate)
                .test()
                .assertNoErrors()
                .assertComplete();

        Game updatedGame = gameDao.getAll()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .get(0);

        assertEquals(toUpdate.getCategory(), updatedGame.getCategory());

    }

    @Test
    public void deleteGameById(){

        gameDao
                .insert(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        int toDelete = gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .get(0)
                .getGameId();

        gameDao
                .deleteById(toDelete)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameDao
                .getAll()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==0);

    }

}