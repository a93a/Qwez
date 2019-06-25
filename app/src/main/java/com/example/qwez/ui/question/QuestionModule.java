package com.example.qwez.ui.question;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.PointsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.router.StartRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    @Provides
    QuestionVMFactory questionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                                        UpdateQuestionAndGameInteractor updateQuestionInteractor,
                                        PointsInteractor pointsInteractor,
                                        StartRouter startRouter){
        return new QuestionVMFactory(getSingleGameAndQuestionsInteractor,
                updateQuestionInteractor,
                pointsInteractor,
                startRouter);
    }

    @Provides
    @Named("question")
    StartRouter startRouter(){
        return new StartRouter();
    }

    @Provides
    PointsInteractor pointsInteractor(GameRepositoryType gameRepositoryType,
                                      FirebaseDatabaseType firebaseDatabaseType,
                                      FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new PointsInteractor(gameRepositoryType, firebaseDatabaseType, firebaseAuthRepositoryType);
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
