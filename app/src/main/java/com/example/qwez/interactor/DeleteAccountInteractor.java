package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.local.GameRepositoryType;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DeleteAccountInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    private final GameRepositoryType gameRepositoryType;

    public DeleteAccountInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType, GameRepositoryType gameRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
        this.gameRepositoryType = gameRepositoryType;
    }

    public Completable delete(String password){
        return firebaseAuthRepositoryType
                .getCurrentUser()
                .take(1)
                .flatMapMaybe(firebaseUser -> firebaseAuthRepositoryType.reAuthenticateUserAndReturnUser(firebaseUser, firebaseUser.getEmail(), password))
                .map(AuthResult::getUser)
                .flatMapCompletable(firebaseAuthRepositoryType::deleteUser)
                .andThen(gameRepositoryType.deleteAll())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
