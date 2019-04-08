package com.example.qwez.ui.splash;

import com.example.qwez.router.StartRouter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SplashVMFactory implements ViewModelProvider.Factory {

    private final StartRouter startRouter;

    public SplashVMFactory(StartRouter startRouter) {
        this.startRouter = startRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(startRouter);
    }

}
