package com.example.qwez.repository.firebase.rxwrapper;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.internal.operators.parallel.ParallelFromPublisher;

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

    /**
     * Re-authenticate User.
     *
     * If trying to either change user primary email address or password, you need to reauthenticate user
     * when trying one of these aforementioned operations. (Re-authentication is needed after certain time passed since
     * last authenticated)
     * @param firebaseUser user to re-authenticate
     * @param email user email address
     * @param password user password
     * @return Completable Emitter
     */
    public static Completable reAuthenticateEmail(FirebaseUser firebaseUser, String email, String password){
        AuthCredential credential = EmailAuthProvider.getCredential(email,password);
        return Completable.create(emitter -> {
            CompletableTask.assign(emitter, firebaseUser.reauthenticate(credential));
        });
    }
}
