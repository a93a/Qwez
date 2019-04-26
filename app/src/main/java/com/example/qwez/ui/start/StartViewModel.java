package com.example.qwez.ui.start;

import android.content.Context;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.DeleteGameInteractor;
import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.Question;
import com.example.qwez.router.SettingsRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

import timber.log.Timber;

public class StartViewModel extends BaseViewModel {

    private final MutableLiveData<List<Question>> questionData = new MutableLiveData<>();
    private final MutableLiveData<List<Game>> gameData = new MutableLiveData<>();
    private final MutableLiveData<String> user = new MutableLiveData<>();

    private final GetQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;
    private final GetUserInteractor getUserInteractor;
    private final DeleteGameInteractor deleteGameInteractor;

    private final SettingsRouter settingsRouter;

    public StartViewModel(GetQuestionsInteractor getQuestionsInteractor,
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

    public void getQuestion(Category category, Difficulty difficulty){
        progress.setValue(true);
        disposable = getQuestionsInteractor
                .getQuestionByCategoryMultiple(category, difficulty)
                .subscribe(this::onQuestions, this::onError);
    }

    public void getAllGames(){
        disposable = getAllGamesInteractor
                .getAllGames()
                .subscribe(this::onGames,this::onError);
    }

    public void getUser(){
        progress.setValue(true);
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser,this::onError);
    }

    private void onUser(FirebaseUser firebaseUser) {
        String username = firebaseUser.getDisplayName();
        if(username == null || username.equals("")){
            username = firebaseUser.getEmail();
        }
        user.setValue(username);
    }

    public void deleteGame(Game game){
        disposable = deleteGameInteractor.deleteGame(game)
                .subscribe(this::onGameDeleted,this::onError);
    }

    private void onGameDeleted(){
        getAllGames();
    }

    public void openSettings(Context context){
        settingsRouter.open(context,false);
    }

    private void onGames(List<Game> games) {
        gameData.setValue(games);
    }

    public MutableLiveData<List<Game>> gameData() {
        return gameData;
    }

    private void onQuestions(List<Question> questions) {
        progress.setValue(false);
        questionData.setValue(questions);
    }

    public void prepare(){
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser,this::onError);
        getAllGames();
    }

    public MutableLiveData<List<Question>> questions() {
        return questionData;
    }

    public MutableLiveData<String> user(){
        return user;
    }
}
