package com.example.qwez.repository.firebase;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Single;

public interface FirebaseStorageRepositoryType {

    Single<UploadTask.TaskSnapshot> uploadPhoto(String user, Uri uri);

    Single<Uri> getDownloadUrl(StorageReference reference);

}
