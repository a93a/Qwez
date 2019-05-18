package com.example.qwez.ui.highscore;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.GetHighscoreInteractor;

public class HighscoreVMFactory implements ViewModelProvider.Factory {

    private final GetHighscoreInteractor getUserHighscoreInteractor;

    public HighscoreVMFactory(GetHighscoreInteractor getUserHighscoreInteractor) {
        this.getUserHighscoreInteractor = getUserHighscoreInteractor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HighscoreViewmodel(getUserHighscoreInteractor);
    }
}
