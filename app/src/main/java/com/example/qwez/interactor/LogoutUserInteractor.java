package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LogoutUserInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public LogoutUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Completable logout(){
        return firebaseAuthRepositoryType.logoutUser()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
