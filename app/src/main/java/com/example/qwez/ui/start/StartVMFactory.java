package com.example.qwez.ui.start;

import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.router.SettingsRouter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StartVMFactory implements ViewModelProvider.Factory {

    private final GetQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;
    private final GetUserInteractor getUserInteractor;
    private final SettingsRouter settingsRouter;

    public StartVMFactory(GetQuestionsInteractor getQuestionsInteractor, GetAllGamesInteractor getAllGamesInteractor, GetUserInteractor getUserInteractor, SettingsRouter settingsRouter) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
        this.getUserInteractor = getUserInteractor;
        this.settingsRouter = settingsRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StartViewModel(getQuestionsInteractor, getAllGamesInteractor, getUserInteractor, settingsRouter);
    }

}
