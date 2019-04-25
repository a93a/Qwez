package com.example.qwez.repository.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface FirebaseAuthRepositoryType {

    Completable signInUserEmailAndPassword(String email, String password);

    Maybe<AuthResult> createUserEmailAndPassword(String email, String password);

    Observable<FirebaseUser> getCurrentUser();

    Completable logoutUser();
}
