package com.example.qwez.interactor;

import androidx.annotation.NonNull;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to sign up a new FirebaseUser
 */
public class SignupInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public SignupInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    /**
     * Sign up new FirebaseUser
     * @param email user email address
     * @param password user password
     * @return Completable
     */
    public Completable signupUser(@NonNull String email, @NonNull String password){
        return Completable.fromMaybe(firebaseAuthRepositoryType.createUserEmailAndPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread()));
    }

}
