package com.example.qwez.ui.question;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.Question;

import java.util.ArrayList;
import java.util.List;

class QuestionViewModel extends BaseViewModel {

    private final MutableLiveData<Question> question = new MutableLiveData<>();

    private final List<GameQuestion> game = new ArrayList<>();

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;

    QuestionViewModel(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
    }

    void getQuestions(int id){
        disposable = getSingleGameAndQuestionsInteractor.getGameQuestions(id)
                .subscribe(this::onQuestions,this::onError);
    }

    private void onQuestions(GameQuestion gameQuestion) {
        game.clear();
        game.add(gameQuestion);
        startQuiz();
    }

    private void startQuiz() {

    }

    MutableLiveData<Question> question(){
        return question;
    }

}
