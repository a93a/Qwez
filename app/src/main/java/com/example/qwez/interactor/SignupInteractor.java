package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Completable;
import io.reactivex.Maybe;
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
     * @param nick user nickname
     * @param password user password
     * @return Completable
     */
    public Completable signupUser(String email, String nick, String password){
        return Completable.fromMaybe(firebaseAuthRepositoryType.createUserEmailAndPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread()));
    }

}
