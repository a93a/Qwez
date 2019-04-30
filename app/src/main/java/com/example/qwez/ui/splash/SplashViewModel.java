package com.example.qwez.ui.splash;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.FirstTimeInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.sharedpref.rxwrapper.SharedPrefsWrapper;
import com.example.qwez.router.LoginRouter;
import com.example.qwez.router.StartRouter;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends BaseViewModel {

    private final StartRouter startRouter;
    private final LoginRouter loginRouter;
    private final GetUserInteractor getUserInteractor;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public SplashViewModel(StartRouter startRouter, LoginRouter loginRouter, GetUserInteractor getUserInteractor) {
        this.startRouter = startRouter;
        this.loginRouter = loginRouter;
        this.getUserInteractor = getUserInteractor;
    }

    public void getUser(){
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser, this::onError);
    }

    public void init(){
    }

    private void onUser(FirebaseUser firebaseUser) {
        user.setValue(firebaseUser);
    }

    public void openStart(Context context){
        startRouter.open(context, true);
    }

    public void openLogin(Context context){
        loginRouter.open(context, true);
    }

    public MutableLiveData<FirebaseUser> user(){
        return user;
    }

    @Override
    protected void onError(Throwable throwable) {
        if(throwable instanceof NullPointerException){
            user.setValue(null);
        }else{
            super.onError(throwable);
        }
    }
}
