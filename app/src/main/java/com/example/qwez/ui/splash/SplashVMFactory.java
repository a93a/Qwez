package com.example.qwez.ui.splash;

import com.example.qwez.interactor.GetAllGamesAndQuestionsInteractor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SplashVMFactory implements ViewModelProvider.Factory {


    public SplashVMFactory() {
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel();
    }

}
