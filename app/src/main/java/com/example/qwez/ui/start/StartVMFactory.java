package com.example.qwez.ui.start;

import com.example.qwez.interactor.DeleteGameInteractor;
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
    private final DeleteGameInteractor deleteGameInteractor;

    public StartVMFactory(GetQuestionsInteractor getQuestionsInteractor,
                          GetAllGamesInteractor getAllGamesInteractor,
                          GetUserInteractor getUserInteractor,
                          SettingsRouter settingsRouter,
                          DeleteGameInteractor deleteGameInteractor) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
        this.getUserInteractor = getUserInteractor;
        this.settingsRouter = settingsRouter;
        this.deleteGameInteractor = deleteGameInteractor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StartViewModel(getQuestionsInteractor, getAllGamesInteractor, getUserInteractor, settingsRouter, deleteGameInteractor);
    }

}
