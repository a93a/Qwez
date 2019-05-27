package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DeleteAccountInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public DeleteAccountInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Completable delete(String password){
        return firebaseAuthRepositoryType
                .getCurrentUser()
                .take(1)
                .flatMapMaybe(firebaseUser -> firebaseAuthRepositoryType.reAuthenticateUserAndReturnUser(firebaseUser, firebaseUser.getEmail(), password))
                .map(AuthResult::getUser)
                .flatMapCompletable(firebaseAuthRepositoryType::deleteUser)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
