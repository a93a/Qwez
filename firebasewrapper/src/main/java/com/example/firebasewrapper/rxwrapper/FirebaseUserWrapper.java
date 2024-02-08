package com.example.firebasewrapper.rxwrapper;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public final class FirebaseUserWrapper {


    private FirebaseUserWrapper(){

    }

    /**
     * Change Firebase user password
     * @param firebaseUser user to change password on
     * @param newPassord to set as new password
     * @return Completable emitter
     */
    public static Completable updateUserPassword(FirebaseUser firebaseUser, String newPassord){
        return Completable.create(emitter -> CompletableTask.assign(emitter, firebaseUser.updatePassword(newPassord)));
    }

    /**
     * Update Firebase user profile
     * @param firebaseUser user to update
     * @param request profile update request
     * @return Completable Emitter
     */
    public static Completable updateUserProfile(FirebaseUser firebaseUser, UserProfileChangeRequest request){
        return Completable.create(emitter -> CompletableTask.assign(emitter, firebaseUser.updateProfile(request)));
    }

    /**
     * Re-authenticate User.
     *
     * If trying to either change user primary email address or password, you need to reauthenticate user
     * when trying one of these aforementioned operations. (Re-authentication is needed after certain time passed since
     * last authenticated)
     * @param firebaseUser user to re-authenticate
     * @param authCredential AuthCredential of email&password
     * @return Completable Emitter
     */
    public static Completable reAuth(FirebaseUser firebaseUser, AuthCredential authCredential){
        return Completable.create(emitter -> CompletableTask.assign(emitter, firebaseUser.reauthenticate(authCredential)));
    }

    /**
     * Re-authenticate User.
     *
     * If trying to either change user primary email address or password, you need to reauthenticate user
     * when trying one of these aforementioned operations. (Re-authentication is needed after certain time passed since
     * last authenticated)
     * @param firebaseUser user to re-authenticate
     * @param authCredential AuthCredential of email&password
     * @return Completable Emitter
     */
    public static Maybe<AuthResult> reAuthAndGetUser(FirebaseUser firebaseUser, AuthCredential authCredential){
        return Maybe.create(emitter -> MaybeTask.assign(emitter, firebaseUser.reauthenticateAndRetrieveData(authCredential)));
    }

    public static Completable deleteUser(FirebaseUser user){
        return Completable.create(emitter -> CompletableTask.assign(emitter, user.delete()));
    }

}
