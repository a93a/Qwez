package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.sharedpref.SharedPreferencesRepositoryType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FirstTimeInteractorTest {

    @ClassRule
    public static final RxResources rxres = new RxResources();

    @Mock
    SharedPreferencesRepositoryType sharedPreferencesRepositoryType;

    @InjectMocks
    FirstTimeInteractor firstTimeInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkFirstTime() {
        when(sharedPreferencesRepositoryType.getNotFirstTime()).thenReturn(Single.just(true));
        firstTimeInteractor.checkNotFirstTime()
                .test()
                .assertNoErrors()
                .assertValue(true);
        verify(sharedPreferencesRepositoryType).getNotFirstTime();

    }

    @Test
    public void setNotFirstTime() {
        when(sharedPreferencesRepositoryType.setNotFirstTime(true)).thenReturn(Completable.complete());
        firstTimeInteractor.setNotFirstTime()
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(sharedPreferencesRepositoryType).setNotFirstTime(true);
    }
}