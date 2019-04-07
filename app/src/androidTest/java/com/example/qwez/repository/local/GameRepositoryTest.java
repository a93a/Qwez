package com.example.qwez.repository.local;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GameRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameDatabase gameDatabase;
    private GameRepositoryType gameRepositoryType;

    @BeforeClass
    public static void before(){

        //These tests won't complete when running tests, since the tests run on JVM and won't
        //be able to access Android specific scheduler (AndroidSchedulers.MainThread()) we have to
        //change the scheduler to either testscheduler or trampoline.
        //RxAndroidPlugins.reset();   //this class will help us with changing observeOn Scheduler. (WE DONT NEED THIS FOR THIS TEST)
        RxJavaPlugins.reset();  //this class will help us with changing subscribeOn scheduler
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        //Since we don't perform any operations on the AndroidSchedulers.Mainthread() in our gamereposity.class,
        //we don't need to set this.
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();

        gameDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        gameRepositoryType = new GameRepository(gameDatabase.gameDao(), gameDatabase.questionDao());

    }

    @Test
    public void getAllGames() {
    }

    @Test
    public void addGame() {
    }

    @Test
    public void deleteGame() {
    }

    @Test
    public void updateGame() {
    }

    @Test
    public void addQuestion() {

        Game game = new Game("blah", "lah");

        gameRepositoryType
                .addGame(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameRepositoryType
                .getAllGames()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==1);

        Game gameWithID = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(0);

        Question question = new Question(gameWithID.gameId,
                "dsdfd",
                "dfdffd",
                "fffff",
                "dfff" ,
                "ggfg");

        gameRepositoryType
                .addQuestion(question)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameRepositoryType
                .getAllQuestions()
                .test()
                .assertValueCount(1)
                .assertValue(questions -> questions.size()==1);

        int gameId = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(0)
                .gameId;

        int questionId = gameRepositoryType
                .getAllQuestions()
                .test()
                .values()
                .get(0)
                .get(0)
                .getqId();

        assertEquals(questionId, gameId);

        gameRepositoryType
                .deleteGame(gameWithID)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameRepositoryType
                .getAllGames()
                .test()
                .assertValue(games -> games.size()==0);

        gameRepositoryType
                .getAllQuestions()
                .test()
                .assertValue(questions -> questions.size()==0);


    }

    @Test
    public void deleteQuestion() {
    }

    @Test
    public void updateQuestion() {
    }
}