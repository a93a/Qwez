package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.entity.Highscore;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;
import com.google.firebase.auth.FirebaseUser;

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

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GetHighscoreInteractorTest {

    @ClassRule
    public static RxResources rex = new RxResources();

    @Mock
    FirebaseDatabaseType firebaseDatabaseType;

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @Mock
    FirebaseUser user;

    @Mock
    Throwable error;

    @InjectMocks
    GetHighscoreInteractor interactor;

    private static final String UID = "grg43g";
    private static final int HIGHSCORE = 23;
    private List<Highscore> list;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        list = new ArrayList<>();
        Highscore highscore1 = new Highscore("bla", 123);
        Highscore highscore2 = new Highscore("bla2", 1233);
        list.add(highscore1);
        list.add(highscore2);
    }

    @Test
    public void testGetUserScore() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(user));
        when(user.getUid()).thenReturn(UID);
        when(firebaseDatabaseType.getUserHighscore(UID)).thenReturn(Single.just(HIGHSCORE));

        interactor.getUserScore()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(HIGHSCORE);

        verify(firebaseAuthRepositoryType).getCurrentUser();
        verify(user).getUid();
        verify(firebaseDatabaseType).getUserHighscore(UID);
    }

    @Test
    public void testGetUserScoreError() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(user));
        when(user.getUid()).thenReturn(UID);
        when(firebaseDatabaseType.getUserHighscore(UID)).thenReturn(Single.error(error));

        interactor.getUserScore()
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(firebaseAuthRepositoryType).getCurrentUser();
        verify(user).getUid();
        verify(firebaseDatabaseType).getUserHighscore(UID);
    }

    @Test
    public void testGetTop50Highscore() {
        when(firebaseDatabaseType.getTop50Highscores()).thenReturn(Single.just(list));

        TestObserver<List<Highscore>> observer = interactor.getTop50Highscore()
                .test();

        verify(firebaseDatabaseType).getTop50Highscores();

        observer.assertComplete()
                .assertNoErrors()
                .assertValueCount(1);

        List<Highscore> newList = observer.values().get(0);

        assertEquals(newList.get(0), list.get(1));  //checking that list also is sorted
    }

    @Test
    public void testGetTop50HighscoreError() {
        when(firebaseDatabaseType.getTop50Highscores()).thenReturn(Single.error(error));

        interactor.getTop50Highscore()
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(firebaseDatabaseType).getTop50Highscores();
    }

}