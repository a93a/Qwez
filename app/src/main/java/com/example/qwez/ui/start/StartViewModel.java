package com.example.qwez.ui.start;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.DeleteGameInteractor;
import com.example.qwez.interactor.FetchQuestionsInteractor;
import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.local.Game;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.router.SettingsRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class StartViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> questionData = new MutableLiveData<>();
    private final MutableLiveData<List<Game>> gameData = new MutableLiveData<>();
    private final MutableLiveData<String> user = new MutableLiveData<>();
    private final MutableLiveData<Uri> userPhotoUrl = new MutableLiveData<>();

    private final FetchQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;
    private final GetUserInteractor getUserInteractor;
    private final DeleteGameInteractor deleteGameInteractor;

    private final SettingsRouter settingsRouter;
    private final QuestionRouter questionRouter;

    public StartViewModel(FetchQuestionsInteractor getQuestionsInteractor,
                          GetAllGamesInteractor getAllGamesInteractor,
                          GetUserInteractor getUserInteractor,
                          SettingsRouter settingsRouter,
                          DeleteGameInteractor deleteGameInteractor,
                          QuestionRouter questionRouter) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
        this.getUserInteractor = getUserInteractor;
        this.settingsRouter = settingsRouter;
        this.deleteGameInteractor = deleteGameInteractor;
        this.questionRouter = questionRouter;
    }

    public void getQuestion(Category category, Difficulty difficulty){
        progress.setValue(true);
        disposable = getQuestionsInteractor
                .getQuestionByCategoryMultiple(category, difficulty)
                .subscribe(this::onQuestion, this::onError);
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

    public void openQuestion(Context context, int qId){
        questionRouter.open(context, qId);
    }

    private void onUser(FirebaseUser firebaseUser) {
        String username = firebaseUser.getDisplayName();
        if(username == null || username.equals("")){
            username = firebaseUser.getEmail();
        }
        user.setValue(username);

        Uri uri = firebaseUser.getPhotoUrl();
        if(uri != null){
            userPhotoUrl.setValue(uri);
        }else{
            userPhotoUrl.setValue(Uri.parse("file:///android_asset/user.png"));
        }
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

    private void onQuestion() {
        progress.setValue(false);
        questionData.setValue(true);
    }

    public void prepare(){
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser,this::onError);
        getAllGames();
    }

    public MutableLiveData<Boolean> questions() {
        return questionData;
    }

    public MutableLiveData<String> user(){
        return user;
    }

    public MutableLiveData<Uri> userPhotoUrl(){
        return userPhotoUrl;
    }
}
