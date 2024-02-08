package com.example.firebasewrapper.rxwrapper;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * RxJava2 wrapper class which wraps Firebase operations in RxJava Observables.
 */
public final class FirebaseAuthWrapper {

    private FirebaseAuthWrapper(){
        //private constructor to avoid instantiation
    }

    /**
     * Sign in FirebaseUser with email address and password.
     * Returns a Maybe that will emit a FirebaseUser on login success, otherwise emits onError
     * @param firebaseAuth FirebaseAuth. Dagger provided
     * @param email FirebaseUser email address
     * @param password FirebaseUser password
     * @return Maybe emitter
     */
    public static Maybe<AuthResult> signInUserWithEmailAndPassword(FirebaseAuth firebaseAuth, String email, String password){
        return Maybe.create(emitter -> MaybeTask.assign(emitter, firebaseAuth.signInWithEmailAndPassword(email, password)));
    }

    /**
     * Creates a FirebaseUser with email address and password
     * Returns a Maybe that will emit the newly created FirebaseUser on singup success, otherwise emits onError
     * @param firebaseAuth FirebaseAuth. Dagger provided
     * @param email FirebaseUser email address
     * @param password FirebaseUser password
     * @return Maybe emitter
     */
    public static Maybe<AuthResult> createUserEmailAndPassword(FirebaseAuth firebaseAuth, String email, String password){
        return Maybe.create(emitter -> MaybeTask.assign(emitter, firebaseAuth.createUserWithEmailAndPassword(email, password)));
    }

    /**
     *  Get an Observable of FirebaseAuth that will emit onNext(FirebaseAuth) on each FirebaseAuth update in the
     *  Firebase database.
     * @param firebaseAuth FirebaseAuth. Dagger provided
     * @return Observable that emits FirebaseAuth
     */
    public static Observable<FirebaseAuth> observeUserAuthState(FirebaseAuth firebaseAuth){
        return Observable.create(emitter -> {
            final FirebaseAuth.AuthStateListener authStateListener = emitter::onNext;
            firebaseAuth.addAuthStateListener(authStateListener);
            emitter.setCancellable(() -> firebaseAuth.removeAuthStateListener(authStateListener));
        });
    }

}
