package com.example.qwez.ui.highscore;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.qwez.entity.Highscore;
import com.example.qwez.interactor.GetHighscoreInteractor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HighscoreViewmodelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    GetHighscoreInteractor getHighscoreInteractor;

    @InjectMocks
    HighscoreViewmodel viewmodel;

    private LifecycleRegistry lifecycleRegistry;
    private Observer<List<Highscore>> top50Observer;
    private Observer<Integer> highscoreObserver;
    private List<Highscore> highscores;

    private static final int USER_SCORE = 5;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        top50Observer = (Observer<List<Highscore>>)mock(Observer.class);
        highscoreObserver = (Observer<Integer>)mock(Observer.class);

        highscores = new ArrayList<>();
        Highscore highscore1 = new Highscore("test", 123);
        Highscore highscore2 = new Highscore("test", 123);
        Highscore highscore3 = new Highscore("test", 123);
        highscores.add(highscore1);
        highscores.add(highscore2);
        highscores.add(highscore3);

    }

    @Test
    public void getUserHighscore() {
        when(getHighscoreInteractor.getUserScore()).thenReturn(Single.just(USER_SCORE));

        viewmodel.yourscore().observe(lifecycleOwner,highscoreObserver);
        viewmodel.getUserHighscore();

        verify(getHighscoreInteractor).getUserScore();
        verify(highscoreObserver).onChanged(USER_SCORE);
    }

    @Test
    public void getHighscores() {
        when(getHighscoreInteractor.getTop50Highscore()).thenReturn(Single.just(highscores));

        viewmodel.highscore().observe(lifecycleOwner,top50Observer);
        viewmodel.getHighscores();

        verify(getHighscoreInteractor).getTop50Highscore();
        verify(top50Observer).onChanged(highscores);

    }

}