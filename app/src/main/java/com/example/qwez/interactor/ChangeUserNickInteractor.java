package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.auth.User;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class ChangeUserNickInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public ChangeUserNickInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Completable ChangeNick(String nick){
        return firebaseAuthRepositoryType.getCurrentUser()
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserNick(firebaseUser, nick))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
