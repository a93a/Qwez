package com.example.qwez.repository.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface FirebaseAuthRepositoryType {

    Completable signInUserEmailAndPassword(String email, String password);

    Maybe<AuthResult> createUserEmailAndPassword(String email, String password);

    Observable<FirebaseUser> getCurrentUser();
}
