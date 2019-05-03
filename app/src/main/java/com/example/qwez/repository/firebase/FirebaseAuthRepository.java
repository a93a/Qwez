package com.example.qwez.repository.firebase;

import com.example.qwez.repository.firebase.rxwrapper.FirebaseAuthWrapper;
import com.example.qwez.repository.firebase.rxwrapper.FirebaseUserWrapper;
import com.example.qwez.repository.firebase.rxwrapper.RxWrapperNullException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Firebase repository class to handle all Firebase operations
 */
public class FirebaseAuthRepository implements FirebaseAuthRepositoryType {

    public final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    /**
     * Sign in user with email and password, return Completable.
     * @param email FirebaseUser email address
     * @param password FirebaseUser password
     * @return Completable that emits onComplete when success
     */
    @Override
    public Completable signInUserEmailAndPassword(String email, String password) {
        return Completable.fromMaybe(FirebaseAuthWrapper
                .signInUserWithEmailAndPassword(firebaseAuth, email, password))
                .subscribeOn(Schedulers.io());
    }

    /**
     *  Create user with email and password, return Maybe that emits AuthResult
     * @param email FirebaseUser email address
     * @param password FirebaseUser password
     * @return Completable that emits onComplete when user created successfully
     */
    @Override
    public Maybe<AuthResult> createUserEmailAndPassword(String email, String password) {
        return FirebaseAuthWrapper.createUserEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io());

    }

    /**
     * Get Observable that emits current signed in user. Observable emits onError if
     * no user currently authorized.
     * @return Observable that emits onNext(FirebaseUser) on subscribe, and then emits onNext(FirebaseUser) on
     * each Firebase database update on user
     */
    @Override
    public Observable<FirebaseUser> getCurrentUser() {
        return FirebaseAuthWrapper.observeUserAuthState(firebaseAuth)
                .map(FirebaseAuth::getCurrentUser)
                .switchIfEmpty(observer -> {
                    observer.onError(new RxWrapperNullException(RxWrapperNullException.NO_CURRENT_USER));
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * Log out current authorized user. Returns Completable with operation result.
     * @return Completable that emits onComplete on success
     */
    @Override
    public Completable logoutUser(){
        return Completable.create(emitter -> {
                    FirebaseAuth.getInstance().signOut();
                    emitter.onComplete();
        })
                .subscribeOn(Schedulers.io());
    }

    /**
     * Change current user password. Returns Completable with operation result.
     * @param newPassword to set
     * @return Completable that emits onComplete on success
     */
    @Override
    public Completable changeUserPassword(FirebaseUser firebaseUser, String newPassword) {
        return FirebaseUserWrapper.updateUserPassword(firebaseUser, newPassword)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Change current user nickname. Return Completable with operation results
     * @param firebaseUser current user
     * @param newNick to set
     * @return Completable that emits onComplete on success
     */
    @Override
    public Completable changeUserNick(FirebaseUser firebaseUser, String newNick) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(newNick)
                .build();
        return FirebaseUserWrapper.updateUserProfile(firebaseUser, request)
                .subscribeOn(Schedulers.io());
    }


}
