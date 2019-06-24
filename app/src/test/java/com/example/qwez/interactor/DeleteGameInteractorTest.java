package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Game;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DeleteGameInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    GameRepositoryType gameRepositoryType;

    @Mock
    Throwable error;

    @InjectMocks
    DeleteGameInteractor deleteGameInteractor;

    Game game;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        game = new Game("cat", "diff");
    }

    @Test
    public void deleteGame() {
        when(gameRepositoryType.deleteGame(game)).thenReturn(Completable.complete());
        deleteGameInteractor.deleteGame(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(gameRepositoryType).deleteGame(game);
    }

    @Test
    public void deleteGameError() {
        when(gameRepositoryType.deleteGame(game)).thenReturn(Completable.error(error));

        deleteGameInteractor.deleteGame(game)
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(gameRepositoryType).deleteGame(game);
    }

}