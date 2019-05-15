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

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RememberUserInteractorTest {

    @Mock
    SharedPreferencesRepositoryType repository;

    @InjectMocks
    RememberUserInteractor interactor;

    @ClassRule
    public static RxResources rxres = new RxResources();

    private static final String REMEMBERED = "test@test.se";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRememberIfExists() {
        when(repository.getRemembered()).thenReturn(Single.just(REMEMBERED));
        when(repository.isRemembered()).thenReturn(Single.just(true));

        TestObserver<String> observer = interactor.getRememberIfExists()
                .test();

        verify(repository).isRemembered();
        verify(repository).getRemembered();

        observer.assertNoErrors()
                .assertComplete()
                .assertValueCount(1);

        assertEquals(REMEMBERED, observer.values().get(0));


    }

    @Test
    public void getRememberIfExistsError(){
        when(repository.isRemembered()).thenReturn(Single.just(false));

        TestObserver<String> testObserver = interactor.getRememberIfExists()
                .test();

        verify(repository).isRemembered();

        testObserver.assertComplete()
                .assertNoValues();


    }
}