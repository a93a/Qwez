package com.example.qwez.repository.firebase.rxwrapper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.observers.TestObserver;

import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.EMAIL;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.EXCEPTION;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.PASSWORD;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.setupTask;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnCompleteListener;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnFailureListener;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnSuccessListener;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FirebaseAuthWrapperTest {

    @Mock
    Task<AuthResult> authResultTask;
    @Mock
    FirebaseUser firebaseUser;
    @Mock
    FirebaseAuth firebaseAuth;
    @Mock
    AuthResult authResult;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        setupTask(authResultTask);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);

    }

    @Test
    public void signInUserWithEmailAndPassword() {

        when(firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)).thenReturn(authResultTask);

        TestObserver<AuthResult> testObserver = FirebaseAuthWrapper.signInUserWithEmailAndPassword(firebaseAuth, EMAIL, PASSWORD)
                .test();

        testOnSuccessListener.getValue().onSuccess(authResult);
        testOnCompleteListener.getValue().onComplete(authResultTask);

        testObserver.assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(authResult);

        verify(firebaseAuth).signInWithEmailAndPassword(EMAIL, PASSWORD);

    }

    @Test
    public void signInUserWithEmailAndPasswordError() {

        when(firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)).thenReturn(authResultTask);

        TestObserver<AuthResult> testObserver = FirebaseAuthWrapper.signInUserWithEmailAndPassword(firebaseAuth, EMAIL, PASSWORD)
                .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        testObserver.assertError(EXCEPTION)
                .assertNotComplete();

        verify(firebaseAuth).signInWithEmailAndPassword(EMAIL, PASSWORD);

    }

    @Test
    public void createUserEmailAndPassword() {

        when(firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)).thenReturn(authResultTask);

        TestObserver<AuthResult> authTestObserver = FirebaseAuthWrapper
                .createUserEmailAndPassword(firebaseAuth, EMAIL, PASSWORD)
                .test();

        testOnSuccessListener.getValue().onSuccess(authResult);
        testOnCompleteListener.getValue().onComplete(authResultTask);

        authTestObserver.assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(authResult);

        verify(firebaseAuth).createUserWithEmailAndPassword(EMAIL, PASSWORD);

    }

    @Test
    public void createUserEmailAndPasswordError() {

        when(firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)).thenReturn(authResultTask);

        TestObserver<AuthResult> testObserver = FirebaseAuthWrapper
                .createUserEmailAndPassword(firebaseAuth, EMAIL, PASSWORD)
                .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        testObserver.assertError(EXCEPTION)
                .assertNotComplete();

        verify(firebaseAuth).createUserWithEmailAndPassword(EMAIL, PASSWORD);

    }

    @Test
    public void observeUserAuthState() {

        TestObserver<FirebaseAuth> testObserver = FirebaseAuthWrapper.observeUserAuthState(firebaseAuth)
                .test();

        ArgumentCaptor<FirebaseAuth.AuthStateListener> argumentCaptor = ArgumentCaptor.forClass(FirebaseAuth.AuthStateListener.class);
        verify(firebaseAuth).addAuthStateListener(argumentCaptor.capture());
        argumentCaptor.getValue().onAuthStateChanged(firebaseAuth);

        testObserver.assertNoErrors()
                .assertValueCount(1)
                .assertSubscribed()
                .assertNotComplete()
                .assertValue(firebaseAuth);

    }


}