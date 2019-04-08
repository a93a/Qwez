package com.example.qwez.ui.splash;

import android.content.Context;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.router.StartRouter;

public class SplashViewModel extends BaseViewModel {

    private final StartRouter startRouter;

    public SplashViewModel(StartRouter startRouter) {
        this.startRouter = startRouter;
    }

    public void openStart(Context context){
        startRouter.open(context, true);
    }

}
