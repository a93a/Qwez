package com.example.qwez.ui.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginVMFactory loginVMFactory(){
        return new LoginVMFactory();
    }

}
