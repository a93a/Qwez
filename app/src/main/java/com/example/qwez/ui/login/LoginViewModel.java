package com.example.qwez.ui.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.LoginUserInteractor;
import com.example.qwez.interactor.SignupInteractor;
import com.example.qwez.router.StartRouter;

class LoginViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> signUp = new MutableLiveData<>();

    private final LoginUserInteractor loginUserInteractor;
    private final SignupInteractor signupInteractor;
    private final StartRouter startRouter;

    LoginViewModel(LoginUserInteractor loginUserInteractor,
                          SignupInteractor signupInteractor,
                          StartRouter startRouter) {
        this.loginUserInteractor = loginUserInteractor;
        this.signupInteractor = signupInteractor;
        this.startRouter = startRouter;
    }

    void logInUser(String email, String password){
        progress.setValue(true);
        disposable = loginUserInteractor.login(email, password)
                .subscribe(this::onLoginSuccess, this::onError);
    }

    void signupUser(String email, String password){
        progress.setValue(true);
        disposable = signupInteractor.signupUser(email, password)
                .subscribe(this::onSignupSuccess, this::onError);
    }

    private void onSignupSuccess() {
        progress.setValue(false);
        signUp.setValue(true);
    }

    private void onLoginSuccess() {
        progress.setValue(false);
        logIn.setValue(true);
    }

    void openStart(Context context, boolean clearStack){
        startRouter.open(context,clearStack);
    }

    MutableLiveData<Boolean> login() {
        return logIn;
    }

    MutableLiveData<Boolean> singup() {
        return signUp;
    }
}
