package com.example.qwez.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.LoginUserInteractor;
import com.example.qwez.interactor.SignupInteractor;
import com.example.qwez.router.StartRouter;

public class LoginVMFactory implements ViewModelProvider.Factory {

    private final LoginUserInteractor loginUserInteractor;
    private final SignupInteractor signupInteractor;
    private final StartRouter startRouter;

    LoginVMFactory(LoginUserInteractor loginUserInteractor,
                          SignupInteractor signupInteractor,
                          StartRouter startRouter) {
        this.loginUserInteractor = loginUserInteractor;
        this.signupInteractor = signupInteractor;
        this.startRouter = startRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(loginUserInteractor, signupInteractor, startRouter);
    }

}
