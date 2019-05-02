package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.Game;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GetAllGamesInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    GameRepositoryType gameRepositoryType;

    @InjectMocks
    GetAllGamesInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllGames() {
        Game game = new Game("1", "2");
        Game game2 = new Game("3", "3");
        List<Game> listOfGames = new ArrayList<>();
        listOfGames.add(game);
        listOfGames.add(game2);

        when(gameRepositoryType.getAllGames()).thenReturn(Flowable.just(listOfGames));

        int size = interactor.getAllGames()
                .test()
                .assertValueCount(1)
                .values()
                .get(0)
                .size();

        assertEquals(2, size);
        verify(gameRepositoryType).getAllGames();
    }
}