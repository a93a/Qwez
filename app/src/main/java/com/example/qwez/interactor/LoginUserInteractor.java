package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginUserInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public LoginUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Completable login(String email, String password){
        return firebaseAuthRepositoryType.signInUserEmailAndPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
