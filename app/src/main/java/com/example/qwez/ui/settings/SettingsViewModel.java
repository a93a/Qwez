package com.example.qwez.ui.settings;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.ChangeProfilePhotoInteractor;
import com.example.qwez.interactor.ChangeUserNickInteractor;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

public class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();
    private final MutableLiveData<Boolean> nickChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> photoChange = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeUserNickInteractor changeUserNickInteractor;
    private final ChangeProfilePhotoInteractor changeProfilePhotoInteractor;

    public SettingsViewModel(LogoutUserInteractor logoutUserInteractor,
                             ChangeUserPasswordInteractor changeUserPasswordInteractor,
                             LoginRouter loginRouter,
                             ChangeUserNickInteractor changeUserNickInteractor,
                             ChangeProfilePhotoInteractor changeProfilePhotoInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeUserNickInteractor = changeUserNickInteractor;
        this.changeProfilePhotoInteractor = changeProfilePhotoInteractor;
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

    public void changePhoto(Uri uri){
        progress.setValue(true);
        disposable = changeProfilePhotoInteractor.changeProfilePhoto(uri)
                .subscribe(this::onPhotoChanged,this::onError);
    }

    private void onPhotoChanged(){
        progress.setValue(false);
        photoChange.setValue(true);
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

    public MutableLiveData<Boolean> photoChange(){
        return photoChange;
    }

}


