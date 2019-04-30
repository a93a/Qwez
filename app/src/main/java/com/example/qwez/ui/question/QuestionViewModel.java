package com.example.qwez.ui.question;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.repository.local.GameQuestion;

import java.util.List;

public class QuestionViewModel extends BaseViewModel {

    private final MutableLiveData<GameQuestion> questions = new MutableLiveData<>();

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;

    public QuestionViewModel(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
    }

    public void getQuestions(int id){
        disposable = getSingleGameAndQuestionsInteractor.getGameQuestions(id)
                .subscribe(this::onQuestions,this::onError);
    }


    private void onQuestions(GameQuestion gameQuestion) {
        questions.setValue(gameQuestion);
    }

    public MutableLiveData<GameQuestion> questions() {
        return questions;
    }

}
