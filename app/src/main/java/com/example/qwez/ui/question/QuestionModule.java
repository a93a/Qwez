package com.example.qwez.ui.question;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;
import com.example.qwez.repository.local.GameRepositoryType;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    @Provides
    QuestionVMFactory questionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                                        UpdateQuestionAndGameInteractor updateQuestionInteractor){
        return new QuestionVMFactory(getSingleGameAndQuestionsInteractor,
                updateQuestionInteractor);
    }

    @Provides
    UpdateQuestionAndGameInteractor updateQuestionInteractor(GameRepositoryType gameRepositoryType){
        return new UpdateQuestionAndGameInteractor(gameRepositoryType);
    }

    @Provides
    GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor(GameRepositoryType gameRepositoryType){
        return new GetSingleGameAndQuestionsInteractor(gameRepositoryType);
    }

}
