package com.example.qwez.ui.question;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;

public class QuestionVMFactory implements ViewModelProvider.Factory {

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;
    private final UpdateQuestionAndGameInteractor updateQuestionInteractor;

    QuestionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                      UpdateQuestionAndGameInteractor updateQuestionInteractor) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
        this.updateQuestionInteractor = updateQuestionInteractor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionViewModel(getSingleGameAndQuestionsInteractor,
                updateQuestionInteractor);
    }

}
