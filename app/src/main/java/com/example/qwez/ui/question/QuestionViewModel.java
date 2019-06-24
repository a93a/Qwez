package com.example.qwez.ui.question;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.entity.Answer;
import com.example.qwez.entity.IntroData;
import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.QuestionC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class QuestionViewModel extends BaseViewModel {

    private final MutableLiveData<Question> question = new MutableLiveData<>();
    private final MutableLiveData<IntroData> showIntro = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showEnd = new MutableLiveData<>();
    private final MutableLiveData<Boolean> startQuiz = new MutableLiveData<>();
    private final MutableLiveData<Answer> answer = new MutableLiveData<>();
    private final MutableLiveData<String> timeoutAnswer = new MutableLiveData<>();
    private final MutableLiveData<GameQuestion> game = new MutableLiveData<>();

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;
    private final UpdateQuestionAndGameInteractor updateQuestionInteractor;

    private final List<Question> questionList = new ArrayList<>();
    private Question toUpdate;
    private boolean started;

    QuestionViewModel(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                      UpdateQuestionAndGameInteractor updateQuestionInteractor) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
        this.updateQuestionInteractor = updateQuestionInteractor;
        started = false;
    }

    void prepare(int gameId){
        disposable = getSingleGameAndQuestionsInteractor.getGameQuestions(gameId)
                .doOnNext(game::setValue)
                .doOnNext(this::updateList)
                .doOnNext(gameQuestion -> {
                    if(!started){
                        prepareShowIntro(gameQuestion.game);
                        started = true;
                    }else{
                        updateQuestion();
                    }
                })
                .doOnError(this::onError)
                .subscribe();
    }

    private void updateList(GameQuestion gameQuestion){
        List<Question> sorted = gameQuestion.questions.stream()
                .filter(question2 -> question2.getAnswerChosen()==null)
                .collect(Collectors.toList());
        questionList.clear();
        questionList.addAll(sorted);
    }

    private void prepareShowIntro(Game game){
        String cat = game.getCategory();
        String diff = game.getDifficulty();
        Uri uri = Uri.parse(String.format("file:///android_asset/categories/%s.png", Category.getMap().get(game.getCategory())));
        int answered = game.getAnswered();
        IntroData data = new IntroData(cat,diff,uri, answered);
        showIntro.setValue(data);
    }

    void chooseAnswer(String picked){
        Question q = question.getValue();
        if(q != null){
            toUpdate = q;
            if(picked == null){
                q.setAnswerChosen(QuestionC.TIMEOUT_ANSWER);
                timeoutAnswer.setValue(picked);
            }else{
                q.setAnswerChosen(picked);
                Answer a = new Answer(picked, q.getCorrectAnswer());
                answer.setValue(a);
            }
        }
    }

    /**
     * new way::::::::::::::::::::::::::::::
     * :::::::::::::::::::::::::::::::::::::
     * add column in question, update to wrong answer unless timeout or answer
     */
    private void updateQuestion(){
        if(questionList.size()==0) {
            showEnd.setValue(true);
        }else{
            Question q = questionList.get(0);
            q.setNumber(String.format("%s",10-questionList.size()));
            question.setValue(questionList.get(0));
        }
    }

    void start(){
        if(questionList.size()==0){
            showEnd.setValue(true);
        }else{
            updateQuestion();
            startQuiz.setValue(true);
        }
    }

    void quitQuiz(){
        Question q = question().getValue();
        if(q != null){
            q.setAnswerChosen(QuestionC.TIMEOUT_ANSWER);
            disposable = updateQuestionInteractor.updateQuestion(q)
                    .subscribe();
        }

    }

    void nextQuestion(){
        disposable = updateQuestionInteractor.updateQuestion(question.getValue())
                .subscribe();
    }

    MutableLiveData<Question> question(){
        return question;
    }

    MutableLiveData<IntroData> showIntro(){
        return showIntro;
    }

    MutableLiveData<Boolean> showEnd(){
        return showEnd;
    }

    MutableLiveData<Boolean> startQuiz(){
        return startQuiz;
    }

    MutableLiveData<Answer> answer(){
        return answer;
    }

    MutableLiveData<String> timeoutAnswer() {
        return timeoutAnswer;
    }

    MutableLiveData<GameQuestion> game() {
        return game;
    }


}
