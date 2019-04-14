package com.example.qwez.repository.local;

import android.content.Context;
import android.util.Log;

import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.repository.opentdb.OpenTDB;
import com.example.qwez.repository.opentdb.OpenTDBAPI;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionConverter;
import com.example.qwez.util.QuestionType;
import com.example.qwez.util.URL;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
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
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GameRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameDatabase gameDatabase;
    private GameRepositoryType gameRepositoryType;
    GetQuestionsInteractor getQuestionsInteractor;

    @BeforeClass
    public static void before(){

        //These tests won't complete when running tests, since the tests run on JVM and won't
        //be able to access Android specific scheduler (AndroidSchedulers.MainThread()) we have to
        //change the scheduler to either testscheduler or trampoline.
        RxAndroidPlugins.reset();   //this class will help us with changing observeOn Scheduler. (WE DONT NEED THIS FOR THIS TEST)
        RxJavaPlugins.reset();  //this class will help us with changing subscribeOn scheduler
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        //Since we don't perform any operations on the AndroidSchedulers.Mainthread() in our gamereposity.class,
        //we don't need to set this.
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();

        gameDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        gameRepositoryType = new GameRepository(gameDatabase.gameDao(), gameDatabase.questionDao(),gameDatabase.gameQuestionDao());
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

        Question question = new Question(
                "dsdfd",
                "dfdffd",
                "fffff",
                "dfff" ,
                "ggfg");
        question.setqId(gameWithID.getGameId());

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
                .getGameId();

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
    public void addGameReturnIdAddQuestion(){

        Game game = new Game("blah", "lah");
        Game game2 = new Game("nah", "fah");

        long id = gameRepositoryType
                .addGameReturnId(game)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .values()
                .get(0);

        long id2 = gameRepositoryType
                .addGameReturnId(game2)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .values()
                .get(0);

        int gameId = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(0)
                .getGameId();

        int gameId2 = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(1)
                .getGameId();

        Game game2before = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(1);

        assertEquals(id, gameId);
        assertEquals(id2, gameId2);

        Game gameToDelete = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(0);

        gameRepositoryType
                .deleteGame(gameToDelete)
                .test()
                .assertNoErrors()
                .assertComplete();

        Game game2as1 = gameRepositoryType
                .getAllGames()
                .test()
                .values()
                .get(0)
                .get(0);

        assertEquals(gameId2, game2as1.getGameId());
        assertEquals(game2before.getCategory(), game2as1.getCategory());

    }

}