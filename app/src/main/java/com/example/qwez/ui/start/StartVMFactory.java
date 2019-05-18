package com.example.qwez.ui.start;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qwez.interactor.DeleteGameInteractor;
import com.example.qwez.interactor.FetchQuestionsInteractor;
import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.router.HighscoreRouter;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.router.SettingsRouter;

public class StartVMFactory implements ViewModelProvider.Factory {

    private final FetchQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;
    private final GetUserInteractor getUserInteractor;
    private final SettingsRouter settingsRouter;
    private final DeleteGameInteractor deleteGameInteractor;
    private final QuestionRouter questionRouter;
    private final HighscoreRouter highscoreRouter;

    public StartVMFactory(FetchQuestionsInteractor getQuestionsInteractor,
                          GetAllGamesInteractor getAllGamesInteractor,
                          GetUserInteractor getUserInteractor,
                          SettingsRouter settingsRouter,
                          DeleteGameInteractor deleteGameInteractor,
                          QuestionRouter questionRouter,
                          HighscoreRouter highscoreRouter) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
        this.getUserInteractor = getUserInteractor;
        this.settingsRouter = settingsRouter;
        this.deleteGameInteractor = deleteGameInteractor;
        this.questionRouter = questionRouter;
        this.highscoreRouter = highscoreRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StartViewModel(getQuestionsInteractor,
                getAllGamesInteractor,
                getUserInteractor,
                settingsRouter,
                deleteGameInteractor,
                questionRouter,
                highscoreRouter);
    }

}
