package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteAccountInteractorTest {

    @ClassRule
    public static final RxResources rxres = new RxResources();

    @Mock
    FirebaseUser firebaseUser;
    @Mock
    FirebaseAuthRepositoryType auth;
    @Mock
    AuthResult result;
    @Mock
    FirebaseUser newUser;

    private static final String EMAIL = "test@test.test";
    private static final String PASS = "test123";

    @InjectMocks
    DeleteAccountInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void delete() {
        when(auth.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getEmail()).thenReturn(EMAIL);
        when(auth.reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS)).thenReturn(Maybe.just(result));
        when(result.getUser()).thenReturn(newUser);
        when(auth.deleteUser(newUser)).thenReturn(Completable.complete());

        TestObserver testObserver = interactor.delete(PASS)
                .test();

        verify(firebaseUser).getEmail();
        verify(auth).getCurrentUser();
        verify(auth).reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS);
        verify(result).getUser();
        verify(auth).deleteUser(newUser);

        testObserver.assertNoErrors()
                .assertComplete();
    }
}