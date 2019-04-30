package com.example.qwez.interactor;

import android.content.Context;

import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameDatabase;
import com.example.qwez.repository.local.GameRepository;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDB;
import com.example.qwez.repository.opentdb.OpenTDBAPI;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.repository.opentdb.entity.Question;
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

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class GetQuestionsInteractorTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameRepositoryType gameRepositoryType;
    private OpenTDBType openTDBType;
    private FetchQuestionsInteractor getQuestionsInteractor;

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

        GameDatabase gameDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        gameRepositoryType = new GameRepository(gameDatabase.gameDao(), gameDatabase.questionDao(),gameDatabase.gameQuestionDao());

        //build api
        OpenTDBAPI openTDBAPI = new Retrofit.Builder()
                .baseUrl(URL.URL_END_POINT)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(OpenTDBAPI.class);

        //build remote repository
        openTDBType = new OpenTDB(openTDBAPI);

        //build interactor
        getQuestionsInteractor = new FetchQuestionsInteractor(openTDBType,gameRepositoryType);

    }

    @Test
    public void testInteractorMethodDissected(){

        //dissected the interactor method to invidually test all components in the method stream.

        //test remote
        TestObserver<List<Question>> test = openTDBType
                .getQuestionByCategory(10, 11, Difficulty.EASY.getDifficulty(), QuestionType.MULTIPLE_CHOICE.getType())
                .test();

        test.awaitTerminalEvent();

        List<com.example.qwez.repository.opentdb.entity.Question> beforeConvert = test.values().get(0);

        //convert from remote to local
        List<com.example.qwez.repository.local.Question> afterConvert = QuestionConverter.toDatabase(beforeConvert);

        //add new game
        int id = (int)(long)gameRepositoryType.addGameReturnId(new Game(Category.getAsString(Category.FILMS), Difficulty.EASY.getDifficulty())).blockingGet();

        //set local id to game id
        afterConvert.forEach(question -> question.setqId(id));

        //insert locals into db
        //i set the sae primary key to all. that is not ok. each should have its own
        //foreing key is same, but not primary key!!!!!!!!!!!!!!!!!!!!!!!!
        afterConvert.forEach(question -> gameRepositoryType.addQuestion(question)
                .test().assertNoErrors().assertComplete());

        //get locals and test
        gameRepositoryType.getAllQuestions()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(q -> q.size()==10)
                .values()
                .get(0)
                .forEach(q -> {
                    assertEquals(id, q.getqId());
                    System.out.println(q.getQuestion().toString());   //check visually each question is created correctly
                });

        //dont call assertComplete on Flowable!!! Because flowable dont call complete.
        Game game = gameRepositoryType.getAllGames()
                .test()
                .assertNoErrors()
                .values()
                .get(0)
                .get(0);

        //delete game
        gameRepositoryType.deleteGame(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        gameRepositoryType.getAllGames()
                .test()
                .assertValueCount(1)
                .assertValue(games -> games.size()==0);

        gameRepositoryType.getAllQuestions()
                .test()
                .assertValueCount(1)
                .assertValue(q -> q.size()==0);

    }

}