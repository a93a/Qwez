package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
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
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChangeUserPasswordInteractorTest {

    private static final String EMAIL = "test@test.se";
    private static final String PASS = "test123";
    private static final String NEW_PASS = "test1234";

    @ClassRule
    public static final RxResources rxres = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    @Mock
    FirebaseUser firebaseUser;

    @InjectMocks
    ChangeUserPasswordInteractor changeUserPasswordInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void changeUserPassword() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseAuthRepositoryType.changeUserPassword(firebaseUser, NEW_PASS)).thenReturn(Completable.complete());
        when(firebaseUser.getEmail()).thenReturn(EMAIL);
        when(firebaseAuthRepositoryType.reAuthenticateUser(firebaseUser, EMAIL, PASS)).thenReturn(Completable.complete());

        changeUserPasswordInteractor.changeUserPassword(PASS,NEW_PASS)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).getCurrentUser();
        verify(firebaseAuthRepositoryType).changeUserPassword(firebaseUser, NEW_PASS);

    }
}