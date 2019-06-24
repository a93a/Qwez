package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.local.GameRepositoryType;
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

import static org.mockito.Mockito.never;
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
    GameRepositoryType gameRepositoryType;

    @Mock
    AuthResult result;

    @Mock
    FirebaseUser newUser;

    @Mock
    Throwable error;

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
        when(gameRepositoryType.deleteAll()).thenReturn(Completable.complete());

        TestObserver testObserver = interactor.delete(PASS)
                .test();

        verify(firebaseUser).getEmail();
        verify(auth).getCurrentUser();
        verify(auth).reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS);
        verify(result).getUser();
        verify(auth).deleteUser(newUser);
        verify(gameRepositoryType).deleteAll();

        testObserver.assertNoErrors()
                .assertComplete();
    }

    @Test
    public void deleteErrorReAuth() {
        when(auth.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getEmail()).thenReturn(EMAIL);
        when(auth.reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS)).thenReturn(Maybe.error(error));
        when(result.getUser()).thenReturn(newUser);
        when(auth.deleteUser(newUser)).thenReturn(Completable.complete());
        when(gameRepositoryType.deleteAll()).thenReturn(Completable.complete());

        interactor.delete(PASS)
                .test()
                .assertNotComplete()
                .assertError(error);

        verify(firebaseUser).getEmail();
        verify(auth).getCurrentUser();
        verify(auth).reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS);
        verify(result,never()).getUser();
        verify(auth,never()).deleteUser(newUser);
        verify(gameRepositoryType,never()).deleteAll();
    }

    @Test
    public void deleteErrorDeleteUser() {
        when(auth.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getEmail()).thenReturn(EMAIL);
        when(auth.reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS)).thenReturn(Maybe.just(result));
        when(result.getUser()).thenReturn(newUser);
        when(auth.deleteUser(newUser)).thenReturn(Completable.error(error));
        when(gameRepositoryType.deleteAll()).thenReturn(Completable.complete());

        interactor.delete(PASS)
                .test()
                .assertNotComplete()
                .assertError(error);

        verify(firebaseUser).getEmail();
        verify(auth).getCurrentUser();
        verify(auth).reAuthenticateUserAndReturnUser(firebaseUser, EMAIL, PASS);
        verify(result).getUser();
        verify(auth).deleteUser(newUser);
        verify(gameRepositoryType,never()).deleteAll();
    }

}