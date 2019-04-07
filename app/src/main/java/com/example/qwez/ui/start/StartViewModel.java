package com.example.qwez.ui.start;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class StartViewModel extends BaseViewModel {

    private final MutableLiveData<List<Question>> questionData = new MutableLiveData<>();
    private final MutableLiveData<List<Game>> gameData = new MutableLiveData<>();

    private final GetQuestionsInteractor getQuestionsInteractor;
    private final GetAllGamesInteractor getAllGamesInteractor;

    public StartViewModel(GetQuestionsInteractor getQuestionsInteractor, GetAllGamesInteractor getAllGamesInteractor) {
        this.getQuestionsInteractor = getQuestionsInteractor;
        this.getAllGamesInteractor = getAllGamesInteractor;
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

    public MutableLiveData<List<Question>> questions() {
        return questionData;
    }
}
