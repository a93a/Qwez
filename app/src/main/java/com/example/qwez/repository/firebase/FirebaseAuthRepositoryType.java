package com.example.qwez.repository.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

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

    /**
     * Change current user password. Returns Completable with operation result.
     */
    Completable changeUserPassword(FirebaseUser firebaseUser, String newPassword);

    /**
     * Change current user nickname. Return Completable with operation results
     */
    Completable changeUserNick(FirebaseUser firebaseUser, String newNick);

    /**
     * Re-authenticate current logged in user
     */
    Completable reAuthenticateUser(FirebaseUser firebaseUser, String email, String password);

    /**
     * Re-authenticate current logged in user
     */
    Observable<AuthResult> reAuthenticateUserAndReturnUser(FirebaseUser firebaseUser, String email, String password);

}

