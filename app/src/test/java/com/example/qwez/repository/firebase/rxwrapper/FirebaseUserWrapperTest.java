package com.example.qwez.repository.firebase.rxwrapper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.observers.TestObserver;

import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.EXCEPTION;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.PASSWORD;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.setupTask;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnCompleteListener;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnFailureListener;
import static com.example.qwez.repository.firebase.rxwrapper.FirebaseTestHelper.testOnSuccessListener;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FirebaseUserWrapperTest {

    @Mock
    FirebaseUser firebaseUser;
    @Mock
    Task<Void> voidTask;
    @Mock
    UserProfileChangeRequest userProfileChangeRequest;
    @Mock
    AuthCredential authCredential;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupTask(voidTask);

    }

    @Test
    public void updateUserPassword() {
        when(firebaseUser.updatePassword(PASSWORD)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.updateUserPassword(firebaseUser, PASSWORD)
                .test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).updatePassword(PASSWORD);

        testObserver.assertNoErrors()
                .assertComplete();


    }

    @Test
    public void updateUserPasswordError() {
        when(firebaseUser.updatePassword(PASSWORD)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.updateUserPassword(firebaseUser, PASSWORD)
                .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).updatePassword(PASSWORD);

        testObserver.assertError(EXCEPTION)
                .assertNotComplete();
    }

    @Test
    public void updateUserProfile() {

        when(firebaseUser.updateProfile(userProfileChangeRequest)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.updateUserProfile(firebaseUser, userProfileChangeRequest)
                .test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).updateProfile(userProfileChangeRequest);

        testObserver.assertNoErrors()
                .assertComplete();

    }

    @Test
    public void updateUserProfileError() {

        when(firebaseUser.updateProfile(userProfileChangeRequest)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.updateUserProfile(firebaseUser, userProfileChangeRequest)
                .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).updateProfile(userProfileChangeRequest);

        testObserver.assertError(EXCEPTION)
                .assertNotComplete();

    }

    @Test
    public void reAuthenticateEmail() {
        when(firebaseUser.reauthenticate(authCredential)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.reAuth(firebaseUser, authCredential)
                .test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).reauthenticate(authCredential);

        testObserver.assertNoErrors()
                .assertComplete();
    }

    @Test
    public void reAuthenticateEmailError() {
        when(firebaseUser.reauthenticate(authCredential)).thenReturn(voidTask);

        TestObserver<Void> testObserver = FirebaseUserWrapper.reAuth(firebaseUser, authCredential)
                .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).reauthenticate(authCredential);

        testObserver.assertError(EXCEPTION)
                .assertNotComplete();
    }

}