package com.example.qwez.interactor;

import com.example.qwez.entity.FinishedGame;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;
import com.example.qwez.repository.local.GameRepositoryType;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class PointsInteractor {

    private final GameRepositoryType gameRepositoryType;
    private final FirebaseDatabaseType firebaseDatabaseType;
    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public PointsInteractor(GameRepositoryType gameRepositoryType, FirebaseDatabaseType firebaseDatabaseType, FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
        this.firebaseDatabaseType = firebaseDatabaseType;
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    public Single<FinishedGame> getPoints(int id){
        return gameRepositoryType.getAllCorrectQuestions(id)
                .map(questions -> new FinishedGame(questions.size(), id))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable finishGameAndUploadPoints(int gameId){
        return Single.fromObservable(firebaseAuthRepositoryType.getCurrentUser().take(1))
                .zipWith(gameRepositoryType.getAllCorrectQuestions(gameId),
                        (firebaseUser, questions) -> firebaseDatabaseType.updateHighscore(firebaseUser.getUid(),questions.size()))
                .flatMapCompletable(completable -> gameRepositoryType.getGameById(gameId)
                        .flatMapCompletable(gameRepositoryType::deleteGame))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
