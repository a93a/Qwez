package com.example.qwez.ui.start;

import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDBType;

import dagger.Module;
import dagger.Provides;

@Module
public class StartModule {

    @Provides
    StartVMFactory questionVMFactory(GetQuestionsInteractor getQuestionsInteractor, GetAllGamesInteractor getAllGamesInteractor){
        return new StartVMFactory(getQuestionsInteractor, getAllGamesInteractor);
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
