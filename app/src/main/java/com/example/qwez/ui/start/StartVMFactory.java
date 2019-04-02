package com.example.qwez.ui.start;

import com.example.qwez.interactor.GetQuestionsInteractor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StartVMFactory implements ViewModelProvider.Factory {

    private final GetQuestionsInteractor getQuestionsInteractor;

    public StartVMFactory(GetQuestionsInteractor getQuestionsInteractor) {
        this.getQuestionsInteractor = getQuestionsInteractor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StartViewModel(getQuestionsInteractor);
    }

}
