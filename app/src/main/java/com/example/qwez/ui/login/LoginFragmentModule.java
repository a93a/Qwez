package com.example.qwez.ui.login;

import com.example.qwez.interactor.RememberUserInteractor;
import com.example.qwez.repository.sharedpref.SharedPreferencesRepositoryType;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginFragmentModule {

    @Provides
    RememberUserInteractor rememberUserInteractor(SharedPreferencesRepositoryType sharedPreferencesRepositoryType){
        return new RememberUserInteractor(sharedPreferencesRepositoryType);
    }

}
