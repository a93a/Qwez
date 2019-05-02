package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.GameRepositoryType;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DeleteGameInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    GameRepositoryType gameRepositoryType;

    @InjectMocks
    DeleteGameInteractor deleteGameInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteGame() {
        Game game = new Game("cat", "diff");
        when(gameRepositoryType.deleteGame(game)).thenReturn(Completable.complete());
        deleteGameInteractor.deleteGame(game)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(gameRepositoryType).deleteGame(game);
    }
}