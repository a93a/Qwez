package com.example.qwez.ui.start;

import androidx.appcompat.app.ActionBar;

import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepository;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDBType;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class StartModule {

    @Provides
    StartVMFactory questionVMFactory(GetQuestionsInteractor getQuestionsInteractor,
                                     GetAllGamesInteractor getAllGamesInteractor,
                                     GetUserInteractor getUserInteractor){
        return new StartVMFactory(getQuestionsInteractor, getAllGamesInteractor, getUserInteractor);
    }

    @Provides
    @Named("start")
    GetUserInteractor getUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new GetUserInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    GetQuestionsInteractor getQuestionsInteractor(OpenTDBType openTDBType, GameRepositoryType gameRepositoryType){
        return new GetQuestionsInteractor(openTDBType, gameRepositoryType);
    }

    @Provides
    GetAllGamesInteractor getAllGamesInteractor(GameRepositoryType gameRepositoryType){
        return new GetAllGamesInteractor(gameRepositoryType);
    }

}
