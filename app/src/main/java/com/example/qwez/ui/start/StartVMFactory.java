package com.example.qwez.ui.start;

import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StartVMFactory implements ViewModelProvider.Factory {

    private final GetQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;

    public StartVMFactory(GetQuestionsInteractor getQuestionsInteractor, GetAllGamesInteractor getAllGamesInteractor) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StartViewModel(getQuestionsInteractor, getAllGamesInteractor);
    }

}
