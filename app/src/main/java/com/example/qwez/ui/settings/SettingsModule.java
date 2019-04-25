package com.example.qwez.ui.settings;

import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.router.LoginRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    SettingsVMFactory settingsVMFactory(LogoutUserInteractor logoutUserInteractor,
                                        LoginRouter loginRouter){
        return new SettingsVMFactory(logoutUserInteractor, loginRouter);
    }

    @Provides
    @Named("settings")
    LoginRouter loginRouter(){
        return new LoginRouter();
    }

    @Provides
    LogoutUserInteractor logoutUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new LogoutUserInteractor(firebaseAuthRepositoryType);
    }

}