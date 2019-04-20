package com.example.qwez.ui.splash;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.router.StartRouter;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends BaseViewModel {

    private final StartRouter startRouter;
    private final GetUserInteractor getUserInteractor;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public SplashViewModel(StartRouter startRouter, GetUserInteractor getUserInteractor) {
        this.startRouter = startRouter;
        this.getUserInteractor = getUserInteractor;
    }

    public void getUser(){
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser, this::onError);
    }

    private void onUser(FirebaseUser firebaseUser) {

    }

    public void openStart(Context context){
        startRouter.open(context, true);
    }

    public MutableLiveData<FirebaseUser> user(){
        return user;
    }

}
