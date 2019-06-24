package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.GameQuestion;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GetSingleGameAndQuestionsInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    GameRepositoryType gameRepositoryType;

    @Mock
    Throwable error;

    @InjectMocks
    GetSingleGameAndQuestionsInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getGameQuestions() {
        GameQuestion gameQuestion = new GameQuestion();

        when(gameRepositoryType.getGameQuestionBy(5454)).thenReturn(Flowable.just(gameQuestion));

        int id = 5454;

        interactor.getGameQuestions(id)
                .test()
                .assertValueCount(1)
                .assertValue(gameQuestion);

        verify(gameRepositoryType).getGameQuestionBy(id);
    }

    @Test
    public void getGameQuestionsError() {
        when(gameRepositoryType.getGameQuestionBy(anyInt())).thenReturn(Flowable.error(error));

        int random = 343;

        interactor.getGameQuestions(random)
                .test()
                .assertNoValues()
                .assertError(error);

        verify(gameRepositoryType).getGameQuestionBy(random);
    }
}