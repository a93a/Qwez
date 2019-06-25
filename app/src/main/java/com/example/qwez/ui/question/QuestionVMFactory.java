package com.example.qwez.ui.question;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.PointsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;
import com.example.qwez.router.StartRouter;

public class QuestionVMFactory implements ViewModelProvider.Factory {

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;
    private final UpdateQuestionAndGameInteractor updateQuestionInteractor;
    private final PointsInteractor pointsInteractor;
    private final StartRouter startRouter;

    QuestionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                      UpdateQuestionAndGameInteractor updateQuestionInteractor,
                      PointsInteractor pointsInteractor,
                      StartRouter startRouter) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
        this.updateQuestionInteractor = updateQuestionInteractor;
        this.pointsInteractor = pointsInteractor;
        this.startRouter = startRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionViewModel(getSingleGameAndQuestionsInteractor,
                updateQuestionInteractor,
                pointsInteractor,
                startRouter);
    }

}
