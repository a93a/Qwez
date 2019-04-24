package com.example.qwez.ui.splash;

import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.router.LoginRouter;
import com.example.qwez.router.StartRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashVMFactory splashVMFactory(StartRouter startRouter, GetUserInteractor getUserInteractor, LoginRouter loginRouter){
        return new SplashVMFactory(startRouter, getUserInteractor, loginRouter);
    }

    @Provides
    GetUserInteractor getUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new GetUserInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    LoginRouter loginRouter(){
        return new LoginRouter();
    }

    @Provides
    StartRouter startRouter(){
        return new StartRouter();
    }

}

