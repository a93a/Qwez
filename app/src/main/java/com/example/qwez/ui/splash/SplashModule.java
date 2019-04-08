package com.example.qwez.ui.splash;

import com.example.qwez.router.StartRouter;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashVMFactory splashVMFactory(StartRouter startRouter){
        return new SplashVMFactory(startRouter);
    }

    @Provides
    StartRouter startRouter(){
        return new StartRouter();
    }

}

