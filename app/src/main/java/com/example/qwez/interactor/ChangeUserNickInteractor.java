package com.example.qwez.interactor;

import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class ChangeUserNickInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public ChangeUserNickInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Completable ChangeNick(String nick){
        return firebaseAuthRepositoryType.getCurrentUser().take(1)
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserNick(firebaseUser, nick))
                .doOnComplete(() -> Timber.d("this complete"))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
