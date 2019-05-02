package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.GameRepositoryType;

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

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GetAllGamesAndQuestionsInteractorTest {

    @ClassRule
    public static RxResources rex = new RxResources();

    @Mock
    GameRepositoryType gameRepositoryType;

    @InjectMocks
    GetAllGamesAndQuestionsInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllGamesAndQuestions() {
        GameQuestion gameQuestion = new GameQuestion();
        GameQuestion gameQuestion2 = new GameQuestion();
        GameQuestion gameQuestion3 = new GameQuestion();
        List<GameQuestion> list = new ArrayList<>();
        list.add(gameQuestion);
        list.add(gameQuestion2);
        list.add(gameQuestion3);

        when(gameRepositoryType.getAllGamesAndQuestions()).thenReturn(Flowable.just(list));

        int amountreceived = interactor.getAllGamesAndQuestions()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .size();

        assertEquals(3, amountreceived);


        verify(gameRepositoryType).getAllGamesAndQuestions();
    }
}