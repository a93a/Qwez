package com.example.qwez.interactor;

import com.example.qwez.entity.Highscore;
import com.example.qwez.interactor.rx.Operators;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GetHighscoreInteractor {

    private final FirebaseDatabaseType firebaseDatabaseType;
    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public GetHighscoreInteractor(FirebaseDatabaseType firebaseDatabaseType,
                                  FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseDatabaseType = firebaseDatabaseType;
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Single<Integer> getUserScore(){
        return Single.fromObservable(firebaseAuthRepositoryType
                .getCurrentUser()
                .take(1)
                .flatMapSingle(firebaseUser -> firebaseDatabaseType.getUserHighscore(firebaseUser.getUid())))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Highscore>> getTop50Highscore(){
        return firebaseDatabaseType.getTop50Highscores()
                .map(Operators.SORT_HIGHSCORES)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
