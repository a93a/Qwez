package com.example.qwez.ui.question;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;

public class QuestionVMFactory implements ViewModelProvider.Factory {

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;

    QuestionVMFactory(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionViewModel(getSingleGameAndQuestionsInteractor);
    }

}
