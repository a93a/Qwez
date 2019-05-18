package com.example.qwez.ui.highscore;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetHighscoreInteractor;

import java.util.List;

import timber.log.Timber;

public class HighscoreViewmodel extends BaseViewModel {
    
    private MutableLiveData<List<Integer>> highscore = new MutableLiveData<>();
    private MutableLiveData<Integer> yourscore = new MutableLiveData<>();

    private final GetHighscoreInteractor getUserHighscoreInteractor;

    public HighscoreViewmodel(GetHighscoreInteractor interactor) {
        this.getUserHighscoreInteractor = interactor;
    }

    public void getUserHighscore(){
        progress.setValue(true);
        disposable = getUserHighscoreInteractor
                .getUserScore()
                .subscribe(this::onHighscore,this::onError);
    }

    public void getHighscores(){
        progress.setValue(true);
        disposable = getUserHighscoreInteractor
                .getTop50Highscore()
                .subscribe(this::onScores,this::onError);
    }

    private void onScores(List<Integer> integers) {
        integers.forEach(integer -> Timber.d("HELLO SIR %s",integer));
    }

    private void onHighscore(Integer integer) {
        progress.setValue(false);
        yourscore.setValue(integer);
    }

    public MutableLiveData<List<Integer>> highscore() {
        return highscore;
    }

    public MutableLiveData<Integer> yourscore() {
        return yourscore;
    }
}
