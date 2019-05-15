package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to change user password
 */
public class ChangeUserPasswordInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public ChangeUserPasswordInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    /**
     * Get current user, then change password
     * @param oldPass old password of user
     * @param newPass new password to set
     * @return Completable that emits operation result
     */
    public Completable changeUserPassword(String oldPass, String newPass){
        //version force re auth directly. No checking
        return firebaseAuthRepositoryType.getCurrentUser()
                .take(1)  //limit to 1 firebaseuser to complete stream
                .flatMapCompletable(fu -> firebaseAuthRepositoryType.reAuthenticateUser(fu,fu.getEmail(),oldPass)
                        .andThen(firebaseAuthRepositoryType.changeUserPassword(fu, newPass)))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
