package com.example.qwez.interactor;

import android.net.Uri;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseStorageRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangeProfilePhotoInteractor {

    private final FirebaseAuthRepositoryType authRepo;
    private final FirebaseStorageRepositoryType storageRepo;

    public ChangeProfilePhotoInteractor(FirebaseAuthRepositoryType authRepo,
                                        FirebaseStorageRepositoryType storageRepo) {
        this.authRepo = authRepo;
        this.storageRepo = storageRepo;
    }


    public Completable changeProfilePhoto(Uri uri){
        return authRepo.getCurrentUser()
                .take(1)
                .flatMapCompletable(firebaseUser -> storageRepo.uploadPhoto(firebaseUser.getUid(), uri)
                        .flatMap(taskSnapshot -> storageRepo.getDownloadUrl(taskSnapshot.getMetadata().getReference()))
                        .flatMapCompletable(uploadedUri -> authRepo.changeUserPhoto(firebaseUser, uploadedUri)))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
