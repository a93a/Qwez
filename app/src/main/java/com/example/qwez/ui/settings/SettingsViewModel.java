package com.example.qwez.ui.settings;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

public class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;

    public SettingsViewModel(LogoutUserInteractor logoutUserInteractor, ChangeUserPasswordInteractor changeUserPasswordInteractor, LoginRouter loginRouter) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
    }

    public void logoutUser(){
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout,this::onError);
    }

    public void changePassword(String newPass){
        disposable = changeUserPasswordInteractor.changeUserPassword(newPass)
                .subscribe(this::onPassChange,this::onError);
    }

    private void onPassChange() {
        progress.setValue(false);
        passChanged.setValue(true);
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

    public MutableLiveData<Boolean> passChange(){
        return passChanged;
    }
}
