package com.example.qwez.repository.firebase.rxwrapper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseUserWrapper {

    /**
     * Change Firebase user password
     * @param firebaseUser user to change password on
     * @param newPassord to set as new password
     * @return Completable emitter
     */
    public static Completable updateUserPassword(FirebaseUser firebaseUser, String newPassord){
        return Completable.create(emitter -> {
            CompletableTask.assign(emitter, firebaseUser.updatePassword(newPassord));
        });
    }


}
