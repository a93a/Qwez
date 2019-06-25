package com.example.qwez.interactor;

import androidx.annotation.NonNull;

import com.example.qwez.interactor.rx.Operators;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.local.entity.Question;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.example.qwez.interactor.rx.Operators.GAME_MAPPER;

public class UpdateQuestionAndGameInteractor {

    private final GameRepositoryType gameRepositoryType;

    public UpdateQuestionAndGameInteractor(GameRepositoryType gameRepositoryType) {
        this.gameRepositoryType = gameRepositoryType;
    }

    public Completable updateQuestion(@NonNull Question question){
        return gameRepositoryType.updateQuestion(question)
                //.andThen(gameRepositoryType.getGameById(question.getqId())
                        //.map(GAME_MAPPER)
                        //.flatMapCompletable(gameRepositoryType::updateGame))
                .observeOn(AndroidSchedulers.mainThread());
    }


}
