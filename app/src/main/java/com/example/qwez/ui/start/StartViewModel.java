package com.example.qwez.ui.start;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.GetQuestionsInteractor;
import com.example.qwez.repository.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class StartViewModel extends BaseViewModel {

    private final MutableLiveData<List<Question>> questionData = new MutableLiveData<>();

    private final GetQuestionsInteractor getQuestionsInteractor;

    public StartViewModel(GetQuestionsInteractor getQuestionsInteractor) {
        this.getQuestionsInteractor = getQuestionsInteractor;
    }

    public void getQuestion(Category category, Difficulty difficulty){
        progress.setValue(true);
        disposable = getQuestionsInteractor
                .getQuestionByCategoryMultiple(category, difficulty)
                .subscribe(this::onQuestions, this::onError);
    }

    private void onQuestions(List<Question> questions) {
        progress.setValue(false);
        questionData.setValue(questions);
    }

    public MutableLiveData<List<Question>> questions() {
        return questionData;
    }
}
