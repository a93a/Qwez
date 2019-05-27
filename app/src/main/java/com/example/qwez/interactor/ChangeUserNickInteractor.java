package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangeUserNickInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    private final FirebaseDatabaseType firebaseDatabaseType;

    public ChangeUserNickInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType, FirebaseDatabaseType firebaseDatabaseType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
        this.firebaseDatabaseType = firebaseDatabaseType;
    }

    public Completable ChangeNick(String nick){
        return firebaseAuthRepositoryType.getCurrentUser()
                .take(1)
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserNick(firebaseUser, nick)
                .doOnComplete(() -> firebaseDatabaseType.updateNick(firebaseUser.getUid(), nick)))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
