package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ChangeUserPasswordInteractorTest {

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    @Mock
    FirebaseUser firebaseUser;

    @InjectMocks
    ChangeUserPasswordInteractor changeUserPasswordInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }


    @Test
    public void changeUserPassword() {
        Mockito.when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        Mockito.when(firebaseAuthRepositoryType.changeUserPassword(firebaseUser, "test123")).thenReturn(Completable.complete());
        changeUserPasswordInteractor.changeUserPassword("test123")
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete();
    }
}