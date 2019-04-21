package com.example.qwez.repository.firebase;

import android.util.Log;

import com.example.qwez.repository.firebase.rxwrapper.FirebaseAuthWrapper;
import com.example.qwez.repository.firebase.rxwrapper.RxWrapperNullException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class FirebaseAuthRepository implements FirebaseAuthRepositoryType {

    public final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Completable signInUserEmailAndPassword(String email, String password) {
        return Completable.fromMaybe(FirebaseAuthWrapper
                .signInUserWithEmailAndPassword(firebaseAuth, email, password))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<AuthResult> createUserEmailAndPassword(String email, String password) {
        return FirebaseAuthWrapper.createUserEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Observable<FirebaseUser> getCurrentUser() {
        return FirebaseAuthWrapper.observeUserAuthState(firebaseAuth)
                .map(firebaseAuth1 -> firebaseAuth1.getCurrentUser())
                .switchIfEmpty(observer -> {
                    observer.onError(new RxWrapperNullException(RxWrapperNullException.NO_CURRENT_USER));
                })
                .subscribeOn(Schedulers.io());
    }

}
