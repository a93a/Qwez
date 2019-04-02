package com.example.qwez.ui.start;

import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.repository.opentdb.OpenTDBType;

import dagger.Module;
import dagger.Provides;

@Module
public class StartModule {

    @Provides
    StartVMFactory questionVMFactory(GetQuestionsInteractor getQuestionsInteractor){
        return new StartVMFactory(getQuestionsInteractor);
    }

    @Provides
    GetQuestionsInteractor getQuestionsInteractor(OpenTDBType openTDBType){
        return new GetQuestionsInteractor(openTDBType);
    }

}
