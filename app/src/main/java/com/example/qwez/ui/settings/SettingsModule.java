package com.example.qwez.ui.settings;

import com.example.qwez.interactor.ChangeUserNickInteractor;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
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
                                        LoginRouter loginRouter,
                                        ChangeUserPasswordInteractor changeUserPasswordInteractor,
                                        ChangeUserNickInteractor changeUserNickInteractor){
        return new SettingsVMFactory(logoutUserInteractor,
                changeUserPasswordInteractor,
                loginRouter,
                changeUserNickInteractor);
    }

    @Provides
    ChangeUserNickInteractor changeUserNickInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new ChangeUserNickInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    ChangeUserPasswordInteractor changeUserPasswordInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new ChangeUserPasswordInteractor(firebaseAuthRepositoryType);
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
