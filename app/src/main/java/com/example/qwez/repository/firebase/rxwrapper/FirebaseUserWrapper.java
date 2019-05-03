package com.example.qwez.repository.firebase.rxwrapper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public final class FirebaseUserWrapper {


    private FirebaseUserWrapper(){
        //private constructor to avoid instantiation
    }

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

    /**
     * Update Firebase user profile
     * @param firebaseUser user to update
     * @param request profile update request
     * @return Completable Emitter
     */
    public static Completable updateUserProfile(FirebaseUser firebaseUser, UserProfileChangeRequest request){
        return Completable.create(emitter -> {
            CompletableTask.assign(emitter, firebaseUser.updateProfile(request));
        });
    }


}
