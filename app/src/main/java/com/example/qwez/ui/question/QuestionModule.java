package com.example.qwez.ui.question;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.repository.local.GameRepositoryType;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    @Provides
    QuestionVMFactory questionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor){
        return new QuestionVMFactory(getSingleGameAndQuestionsInteractor);
    }

    @Provides
    GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor(GameRepositoryType gameRepositoryType){
        return new GetSingleGameAndQuestionsInteractor(gameRepositoryType);
    }

}
