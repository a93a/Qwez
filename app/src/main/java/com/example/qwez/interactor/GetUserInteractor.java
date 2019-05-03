package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to get FirebaseUser
 */
public class GetUserInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public GetUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    /**
     * Get user
     * @return Observable which emits FirebaseUser object
     */
    public Observable<FirebaseUser> getUser(){
        return firebaseAuthRepositoryType.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
