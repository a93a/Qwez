package com.example.qwez.ui.splash;

import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.router.StartRouter;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashVMFactory splashVMFactory(StartRouter startRouter, GetUserInteractor getUserInteractor){
        return new SplashVMFactory(startRouter, getUserInteractor);
    }

    @Provides
    GetUserInteractor getUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new GetUserInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    StartRouter startRouter(){
        return new StartRouter();
    }

}

