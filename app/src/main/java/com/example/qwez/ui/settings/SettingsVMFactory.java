package com.example.qwez.ui.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

public class SettingsVMFactory implements ViewModelProvider.Factory {

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;

    public SettingsVMFactory(LogoutUserInteractor logoutUserInteractor, ChangeUserPasswordInteractor changeUserPasswordInteractor, LoginRouter loginRouter) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(logoutUserInteractor, changeUserPasswordInteractor, loginRouter);
    }
}
