package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.Question;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UpdateQuestionAndGameInteractorTest {

    @Mock
    GameRepositoryType gameRepositoryType;

    @Mock
    Question question;

    @Mock
    Game game;

    @Mock
    Throwable error;

    @InjectMocks
    UpdateQuestionAndGameInteractor interactor;

    private static final int FAKE_QID = 123;

    @ClassRule
    public static RxResources rex = new RxResources();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateQuestion(){
        when(gameRepositoryType.updateQuestion(question)).thenReturn(Completable.complete());
        when(question.getqId()).thenReturn(FAKE_QID);
        when(gameRepositoryType.getGameById(FAKE_QID)).thenReturn(Single.just(game));
        when(gameRepositoryType.updateGame(game)).thenReturn(Completable.complete());

        interactor.updateQuestion(question)
                .test()
                .assertComplete()
                .assertNoErrors();

        verify(gameRepositoryType).updateQuestion(question);
        verify(question).getqId();
        verify(gameRepositoryType).getGameById(FAKE_QID);
        verify(gameRepositoryType).updateGame(game);
    }

    @Test
    public void updateQuestionError(){
        when(gameRepositoryType.updateQuestion(question)).thenThrow(error);
        when(question.getqId()).thenReturn(FAKE_QID);
        when(gameRepositoryType.getGameById(FAKE_QID)).thenReturn(Single.just(game));
        when(gameRepositoryType.updateGame(game)).thenReturn(Completable.complete());

        interactor.updateQuestion(question)
                .test()
                .assertNotComplete()
                .assertError(error)
                .assertNoValues();

        verify(gameRepositoryType).updateQuestion(question);
        verify(question,never()).getqId();
        verify(gameRepositoryType,never()).getGameById(FAKE_QID);
        verify(gameRepositoryType,never()).updateGame(game);
    }

}