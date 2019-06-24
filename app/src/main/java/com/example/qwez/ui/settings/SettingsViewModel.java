package com.example.qwez.ui.settings;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.ChangeProfilePhotoInteractor;
import com.example.qwez.interactor.ChangeUserNickInteractor;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.DeleteAccountInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;

class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();
    private final MutableLiveData<Boolean> nickChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> photoChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> accountDelete = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeUserNickInteractor changeUserNickInteractor;
    private final ChangeProfilePhotoInteractor changeProfilePhotoInteractor;
    private final DeleteAccountInteractor deleteAccountInteractor;

    SettingsViewModel(LogoutUserInteractor logoutUserInteractor,
                             ChangeUserPasswordInteractor changeUserPasswordInteractor,
                             LoginRouter loginRouter,
                             ChangeUserNickInteractor changeUserNickInteractor,
                             ChangeProfilePhotoInteractor changeProfilePhotoInteractor,
                             DeleteAccountInteractor deleteAccountInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeUserNickInteractor = changeUserNickInteractor;
        this.changeProfilePhotoInteractor = changeProfilePhotoInteractor;
        this.deleteAccountInteractor = deleteAccountInteractor;
    }

    void logoutUser(){
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout,this::onError);
    }

    void changePassword(String oldPass, String newPass){
        progress.setValue(true);
        disposable = changeUserPasswordInteractor.changeUserPassword(oldPass,newPass)
                .subscribe(this::onPassChange,this::onError);
    }

    void changeNick(String newNick){
        progress.setValue(true);
        disposable = changeUserNickInteractor.changeNick(newNick)
                .subscribe(this::onNick,this::onError);
    }

    void changePhoto(Uri uri){
        progress.setValue(true);
        disposable = changeProfilePhotoInteractor.changeProfilePhoto(uri)
                .subscribe(this::onPhotoChanged,this::onError);

    }

    void deleteAccount(String password){
        progress.setValue(true);
        disposable = deleteAccountInteractor.delete(password)
                .subscribe(this::onAccountDeleted,this::onError);
    }

    private void onAccountDeleted(){
        progress.setValue(false);
        accountDelete.setValue(true);
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

    void startLogin(Context context, boolean clearstack){
        loginRouter.open(context, clearstack);
    }

    MutableLiveData<Boolean> logout() {
        return logout;
    }

    MutableLiveData<Boolean> passChange(){
        return passChanged;
    }

    MutableLiveData<Boolean> nickChange(){
        return nickChange;
    }

    MutableLiveData<Boolean> photoChange(){
        return photoChange;
    }

    MutableLiveData<Boolean> accountDelete(){
        return accountDelete;
    }

}


