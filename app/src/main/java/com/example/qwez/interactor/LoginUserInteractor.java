package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to log in FirebaseUser
 */
public class LoginUserInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public LoginUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    /**
     * Log in user
     * @param email user email address
     * @param password user password
     * @return Completable
     */
    public Completable login(String email, String password){
        return firebaseAuthRepositoryType.signInUserEmailAndPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
