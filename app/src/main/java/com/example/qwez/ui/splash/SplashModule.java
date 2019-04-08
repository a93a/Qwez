package com.example.qwez.ui.splash;

import com.example.qwez.di.ApplicationScope;
import com.example.qwez.interactor.GetAllGamesAndQuestionsInteractor;
import com.example.qwez.repository.local.GameRepositoryType;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.Multibinds;

@Module
public class SplashModule {

    @Provides
    SplashVMFactory splashVMFactory(){
        return new SplashVMFactory();
    }


}

