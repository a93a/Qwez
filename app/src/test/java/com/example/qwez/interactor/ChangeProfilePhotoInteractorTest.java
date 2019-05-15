package com.example.qwez.interactor;

import android.net.Uri;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseStorageRepositoryType;
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
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChangeProfilePhotoInteractorTest {

    @Mock
    FirebaseStorageRepositoryType storage;
    @Mock
    FirebaseAuthRepositoryType auth;
    @Mock
    FirebaseUser user;

    @InjectMocks
    ChangeProfilePhotoInteractor interactor;

    private static final String UID = "uid123";

    @Mock
    Uri uri;
    @Mock
    Uri newUri;

    @ClassRule
    public static RxResources rxres = new RxResources();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void changeProfilePhoto() {
        when(auth.getCurrentUser()).thenReturn(Observable.just(user));
        when(user.getUid()).thenReturn(UID);
        when(storage.uploadPhoto(UID, uri)).thenReturn(Single.just(newUri));
        when(auth.changeUserPhoto(user, newUri)).thenReturn(Completable.complete());

        TestObserver testObserver = interactor.changeProfilePhoto(uri)
                .test();

        verify(auth).getCurrentUser();
        verify(user).getUid();
        verify(storage).uploadPhoto(UID, uri);
        verify(auth).changeUserPhoto(user, newUri);

        testObserver.assertNoErrors()
                .assertComplete();

    }
}