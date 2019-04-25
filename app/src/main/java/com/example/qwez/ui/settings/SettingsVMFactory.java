package com.example.qwez.ui.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

public class SettingsVMFactory implements ViewModelProvider.Factory {

    private final LogoutUserInteractor logoutUserInteractor;
    private final LoginRouter loginRouter;

    public SettingsVMFactory(LogoutUserInteractor logoutUserInteractor, LoginRouter loginRouter) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.loginRouter = loginRouter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(logoutUserInteractor, loginRouter);
    }
}
