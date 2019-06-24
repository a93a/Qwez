package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.AuthResult;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Maybe;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SignupInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @Mock
    AuthResult authResult;

    @Mock
    Throwable error;

    @InjectMocks
    SignupInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signupUser() {
        when(firebaseAuthRepositoryType.createUserEmailAndPassword(anyString(), anyString())).thenReturn(Maybe.just(authResult));

        interactor.signupUser("fake", "fake3")
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).createUserEmailAndPassword("fake", "fake3");
    }

    @Test
    public void signupUserError() {
        when(firebaseAuthRepositoryType.createUserEmailAndPassword(anyString(), anyString())).thenReturn(Maybe.error(error));

        interactor.signupUser("fake", "fake3")
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(firebaseAuthRepositoryType).createUserEmailAndPassword("fake", "fake3");
    }
}