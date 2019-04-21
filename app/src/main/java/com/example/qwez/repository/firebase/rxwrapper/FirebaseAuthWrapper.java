package com.example.qwez.repository.firebase.rxwrapper;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

public class FirebaseAuthWrapper {

    public static Maybe<AuthResult> signInUserWithEmailAndPassword(FirebaseAuth firebaseAuth, String email, String password){
        return Maybe.create(emitter -> MaybeTask.assignOnTask(emitter, firebaseAuth.signInWithEmailAndPassword(email, password)));
    }

    public static Maybe<AuthResult> createUserEmailAndPassword(FirebaseAuth firebaseAuth, String email, String password){
        return Maybe.create(emitter -> MaybeTask.assignOnTask(emitter, firebaseAuth.createUserWithEmailAndPassword(email, password)));
    }

    public static Observable<FirebaseAuth> observeUserAuthState(FirebaseAuth firebaseAuth){
        return Observable.create(emitter -> {
            final FirebaseAuth.AuthStateListener authStateListener = emitter::onNext;
            firebaseAuth.addAuthStateListener(authStateListener);
            emitter.setCancellable(() -> firebaseAuth.removeAuthStateListener(authStateListener));
        });
    }

}
