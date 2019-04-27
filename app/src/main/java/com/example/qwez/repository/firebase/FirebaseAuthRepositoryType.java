package com.example.qwez.repository.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * FirebaseAuthRepository interface
 */
public interface FirebaseAuthRepositoryType {

    /**
     * Sign in user with email and password, return Completable.
     */
    Completable signInUserEmailAndPassword(String email, String password);

    /**
     *  Create user with email and password, return Maybe that emits AuthResult
     */
    Maybe<AuthResult> createUserEmailAndPassword(String email, String password);

    /**
     * Get Observable that emits current signed in user. Observable emits onError if
     * no user currently authorized.
     */
    Observable<FirebaseUser> getCurrentUser();

    /**
     * Log out current authorized user. Returns Completable with operation result.
     */
    Completable logoutUser();
}
