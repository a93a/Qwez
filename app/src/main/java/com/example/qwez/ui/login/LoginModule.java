package com.example.qwez.ui.login;

import com.example.qwez.interactor.LoginUserInteractor;
import com.example.qwez.interactor.SignupInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.router.StartRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginVMFactory loginVMFactory(LoginUserInteractor loginUserInteractor,
                                  SignupInteractor signupInteractor,
                                  StartRouter startRouter){
        return new LoginVMFactory(loginUserInteractor, signupInteractor, startRouter);
    }

    @Provides
    @Named("login")
    StartRouter startRouter(){
        return new StartRouter();
    }

    @Provides
    SignupInteractor signupInteractor(FirebaseAuthRepositoryType firebaseAuthRepository){
        return new SignupInteractor(firebaseAuthRepository);
    }

    @Provides
    LoginUserInteractor loginUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new LoginUserInteractor(firebaseAuthRepositoryType);
    }

}
