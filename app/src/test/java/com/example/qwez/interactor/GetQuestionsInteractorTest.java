package com.example.qwez.interactor;

import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionConverter;
import com.example.qwez.util.QuestionType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GetQuestionsInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    GameRepositoryType gameRepositoryType;

    @Mock
    OpenTDBType openTDBType;

    @Mock
    QuestionConverter questionConverter;

    @InjectMocks
    GetQuestionsInteractor getQuestionsInteractor;


    @BeforeClass
    public static void before(){
        //These tests won't complete when running tests, since the tests run on JVM and won't
        //be able to access Android specific scheduler (AndroidSchedulers.MainThread()) we have to
        //change the scheduler to either testscheduler or trampoline.
        RxAndroidPlugins.reset();   //this class will help us with changing observeOn Scheduler. (WE DONT NEED THIS FOR THIS TEST)
        RxJavaPlugins.reset();  //this class will help us with changing subscribeOn scheduler
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getQuestionByCategoryMultiple() {

        int amount = QuestionC.AMOUNT_STANDARD;
        int category = 11;
        String difficulty = "difficulty";
        String type = QuestionType.MULTIPLE_CHOICE.getType();
        List<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add("wa1");
        wrongAnswers.add("wa2");
        wrongAnswers.add("wa3");

        com.example.qwez.repository.opentdb.entity.Question question = new Question(String.valueOf(category), type, difficulty,"blah?" ,"nah" ,wrongAnswers );
        List<Question> list = new ArrayList<>();
        list.add(question);

        List<com.example.qwez.repository.local.Question> convertedList = QuestionConverter.toDatabase(list);

        Mockito.when(openTDBType.getQuestionByCategory(amount, category, difficulty, type))
                .thenReturn(Single.just(list));

        Game game = new Game(String.valueOf(category),difficulty);

        Mockito.when(gameRepositoryType.addGameReturnId(game))
                .thenReturn(Single.just((long)24));

        Mockito.when(gameRepositoryType.addQuestions(convertedList)).thenReturn(Completable.complete());
        Mockito.when(gameRepositoryType.getAllQuestions()).thenReturn(Single.just(convertedList));

    List<com.example.qwez.repository.local.Question> questionList = new ArrayList<>();

    getQuestionsInteractor.getQuestionByCategoryMultiple(Category.FILMS, Difficulty.EASY)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1);
        
    }
}