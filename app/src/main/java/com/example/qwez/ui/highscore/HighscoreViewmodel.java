package com.example.qwez.ui.highscore;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.entity.Highscore;
import com.example.qwez.interactor.GetHighscoreInteractor;

import java.util.List;

import timber.log.Timber;

class HighscoreViewmodel extends BaseViewModel {
    
    private MutableLiveData<List<Highscore>> highscore = new MutableLiveData<>();
    private MutableLiveData<Integer> yourscore = new MutableLiveData<>();

    private final GetHighscoreInteractor getUserHighscoreInteractor;

    HighscoreViewmodel(GetHighscoreInteractor interactor) {
        this.getUserHighscoreInteractor = interactor;
    }

    void getUserHighscore(){
        progress.setValue(true);
        disposable = getUserHighscoreInteractor
                .getUserScore()
                .subscribe(this::onHighscore,this::onError);
    }

    void getHighscores(){
        progress.setValue(true);
        disposable = getUserHighscoreInteractor
                .getTop50Highscore()
                .subscribe(this::onScores,this::onError);
    }

    private void onScores(List<Highscore> scores) {
        progress.setValue(false);
        highscore.setValue(scores);
    }

    private void onHighscore(Integer integer) {
        progress.setValue(false);
        yourscore.setValue(integer);
    }

    MutableLiveData<List<Highscore>> highscore() {
        return highscore;
    }

    MutableLiveData<Integer> yourscore() {
        return yourscore;
    }
}
