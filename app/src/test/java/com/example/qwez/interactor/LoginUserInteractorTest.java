package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginUserInteractorTest {

    @ClassRule
    public static RxResources rexrs = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @InjectMocks
    LoginUserInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void login() {
        when(firebaseAuthRepositoryType.signInUserEmailAndPassword(anyString(), anyString())).thenReturn(Completable.complete());

        interactor.login("test", "test2")
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).signInUserEmailAndPassword("test", "test2");
    }
}