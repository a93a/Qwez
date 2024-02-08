package com.example.qwez.repository.firebase;

import android.net.Uri;

import com.example.firebasewrapper.rxwrapper.FirebaseStorageWrapper;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FirebaseStorageRepository implements FirebaseStorageRepositoryType {

    private final StorageReference reference;
    private final StorageReference userPhoto;

    public FirebaseStorageRepository(StorageReference reference) {
        this.reference = reference;
        userPhoto = reference.child("/user_photo");
    }

    @Override
    public Single<UploadTask.TaskSnapshot> uploadPhoto(String user, Uri uri) {
        StorageReference thisRef = userPhoto.child(String.format("/%s", user));
        return FirebaseStorageWrapper.putFile(thisRef, uri)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Uri> getDownloadUrl(StorageReference reference) {
        return FirebaseStorageWrapper.getDownloadUrl(reference)
                .toSingle()
                .subscribeOn(Schedulers.io());
    }



}
