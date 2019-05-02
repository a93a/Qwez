package com.example.qwez.interactor;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Maybe;

import static org.junit.Assert.*;
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

    @InjectMocks
    SignupInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signupUser() {
        when(firebaseAuthRepositoryType.createUserEmailAndPassword(anyString(), anyString())).thenReturn(Maybe.just(authResult));

        interactor.signupUser("fake", "fake2", "fake3")
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).createUserEmailAndPassword("fake", "fake3");
    }
}