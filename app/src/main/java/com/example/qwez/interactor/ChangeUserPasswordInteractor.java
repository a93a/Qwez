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
     * @param newPassword to set
     * @return Completable that emits operation result
     */
    public Completable changeUserPassword(String newPassword){
        return firebaseAuthRepositoryType.getCurrentUser()
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserPassword(firebaseUser, newPassword))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
