package com.example.qwez.interactor;

import android.net.Uri;

import com.example.qwez.RxResources;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseStorageRepositoryType;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

import static org.mockito.Mockito.never;
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

    @Mock
    UploadTask.TaskSnapshot taskSnapshot;

    @Mock
    StorageReference storageReference;

    @Mock
    Uri uri;

    @Mock
    Uri downloadUri;

    @Mock
    StorageMetadata metadata;

    @Mock
    Throwable error;

    @InjectMocks
    ChangeProfilePhotoInteractor interactor;

    private static final String UID = "uid123";

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
        when(storage.uploadPhoto(UID, uri)).thenReturn(Single.just(taskSnapshot));

        //when(taskSnapshot.getMetadata().getReference()).thenReturn(storageReference);
        //cannot "chain call". must provide mocks of each method in the "chain"
        when(taskSnapshot.getMetadata()).thenReturn(metadata);
        when(metadata.getReference()).thenReturn(storageReference);

        when(storage.getDownloadUrl(storageReference)).thenReturn(Single.just(downloadUri));
        when(auth.changeUserPhoto(user, downloadUri)).thenReturn(Completable.complete());

        TestObserver testObserver = interactor.changeProfilePhoto(uri)
                .test();

        testObserver.assertNoErrors()
                .assertComplete();

        verify(storage).uploadPhoto(UID, uri);
        verify(storage).getDownloadUrl(storageReference);
    }

    @Test
    public void changeProfilePhotoError() {
        when(auth.getCurrentUser()).thenReturn(Observable.just(user));
        when(user.getUid()).thenReturn(UID);
        when(storage.uploadPhoto(UID, uri)).thenReturn(Single.error(error));

        when(taskSnapshot.getMetadata()).thenReturn(metadata);
        when(metadata.getReference()).thenReturn(storageReference);

        when(storage.getDownloadUrl(storageReference)).thenReturn(Single.just(downloadUri));
        when(auth.changeUserPhoto(user, downloadUri)).thenReturn(Completable.complete());

        interactor.changeProfilePhoto(uri)
                .test()
                .assertNotComplete()
                .assertError(error);

        verify(storage).uploadPhoto(UID, uri);
        verify(storage,never()).getDownloadUrl(storageReference);

    }
}