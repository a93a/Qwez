package com.example.qwez.ui.settings;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

import timber.log.Timber;

public class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final LoginRouter loginRouter;

    public SettingsViewModel(LogoutUserInteractor logoutUserInteractor, LoginRouter loginRouter) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.loginRouter = loginRouter;
    }

    public void logoutUser(){
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout,this::onError);
    }

    private void onLogout() {
        progress.setValue(false);
        logout.setValue(true);
    }

    public void startLogin(Context context, boolean clearstack){
        loginRouter.open(context, clearstack);
    }

    public MutableLiveData<Boolean> logout() {
        return logout;
    }
}
