package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionC;
import com.example.qwez.util.QuestionUtil;
import com.example.qwez.util.QuestionType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FetchQuestionsInteractorTest {

    @ClassRule
    public static final RxResources rxres = new RxResources();

    @Mock
    OpenTDBType openTDBType;

    @Mock
    GameRepositoryType gameRepositoryType;

    @Mock
    Throwable error;

    @InjectMocks
    FetchQuestionsInteractor fetchQuestionsInteractor;

    private List<String> corrList;
    private Question q;
    private List<Question> list;
    private Game game;
    private List<com.example.qwez.repository.local.entity.Question>  convList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //setup fake data
        corrList = new ArrayList<>();
        corrList.add("fd");
        corrList.add("fd");
        corrList.add("fd");
        q = new Question("cat", "typ", "diff", "quest", "yh", corrList);
        list = new ArrayList<>();
        list.add(q);
        convList = QuestionUtil.toDatabase(list);
        game = new Game("cat","diff");
    }

    @Test
    public void getQuestionByCategoryMultiple() {
        when(openTDBType.getQuestionByCategory(anyInt(), anyInt(), anyString(), anyString())).thenReturn(Single.just(list));
        when(gameRepositoryType.addGameReturnId(any(Game.class))).thenReturn(Single.just(5L));
        when(gameRepositoryType.addQuestions(anyList())).thenReturn(Completable.complete());

        fetchQuestionsInteractor.getQuestionByCategoryMultiple(Category.ANIMALS, Difficulty.EASY )
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(openTDBType).getQuestionByCategory(QuestionC.AMOUNT_STANDARD, Category.ANIMALS.getCategory(), Difficulty.EASY.getDifficulty(), QuestionType.MULTIPLE_CHOICE.getType());
        verify(gameRepositoryType).addGameReturnId(any(Game.class));
        verify(gameRepositoryType).addQuestions(anyList());
    }

    @Test
    public void getQuestionByCategoryMultipleError() {
        when(openTDBType.getQuestionByCategory(anyInt(), anyInt(), anyString(), anyString())).thenReturn(Single.error(error));
        when(gameRepositoryType.addGameReturnId(any(Game.class))).thenReturn(Single.just(5L));
        when(gameRepositoryType.addQuestions(anyList())).thenReturn(Completable.complete());

        fetchQuestionsInteractor.getQuestionByCategoryMultiple(Category.ANIMALS, Difficulty.EASY )
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(openTDBType).getQuestionByCategory(QuestionC.AMOUNT_STANDARD, Category.ANIMALS.getCategory(), Difficulty.EASY.getDifficulty(), QuestionType.MULTIPLE_CHOICE.getType());
        verify(gameRepositoryType, never()).addGameReturnId(any(Game.class));
        verify(gameRepositoryType, never()).addQuestions(anyList());
    }
}