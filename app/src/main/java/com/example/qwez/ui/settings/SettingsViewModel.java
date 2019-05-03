package com.example.qwez.ui.settings;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.ChangeUserNickInteractor;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

import timber.log.Timber;

public class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();
    private final MutableLiveData<Boolean> nickChange = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeUserNickInteractor changeUserNickInteractor;

    public SettingsViewModel(LogoutUserInteractor logoutUserInteractor,
                             ChangeUserPasswordInteractor changeUserPasswordInteractor,
                             LoginRouter loginRouter,
                             ChangeUserNickInteractor changeUserNickInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeUserNickInteractor = changeUserNickInteractor;
    }

    public void logoutUser(){
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout,this::onError);
    }

    public void changePassword(String oldPass, String newPass){
        progress.setValue(true);
        disposable = changeUserPasswordInteractor.changeUserPassword(oldPass,newPass)
                .subscribe(this::onPassChange,this::onError);
    }

    public void changeNick(String newNick){
        progress.setValue(true);
        disposable = changeUserNickInteractor.ChangeNick(newNick)
                .subscribe(this::onNick,this::onError);
    }

    private void onNick() {
        progress.setValue(false);
        nickChange.setValue(true);
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

    public MutableLiveData<Boolean> nickChange(){
        return nickChange;
    }

}


