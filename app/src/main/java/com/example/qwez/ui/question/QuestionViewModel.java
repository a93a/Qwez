package com.example.qwez.ui.question;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.entity.Answer;
import com.example.qwez.entity.FinishedGame;
import com.example.qwez.entity.IntroData;
import com.example.qwez.interactor.GetSingleGameAndQuestionsInteractor;
import com.example.qwez.interactor.PointsInteractor;
import com.example.qwez.interactor.UpdateQuestionAndGameInteractor;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;
import com.example.qwez.router.StartRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.QuestionC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import timber.log.Timber;

class QuestionViewModel extends BaseViewModel {

    private final MutableLiveData<Question> question = new MutableLiveData<>();
    private final MutableLiveData<IntroData> showIntro = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showEnd = new MutableLiveData<>();
    private final MutableLiveData<Boolean> startQuiz = new MutableLiveData<>();
    private final MutableLiveData<Answer> answer = new MutableLiveData<>();
    private final MutableLiveData<String> timeoutAnswer = new MutableLiveData<>();
    private final MutableLiveData<GameQuestion> game = new MutableLiveData<>();
    private final MutableLiveData<FinishedGame> finishedGame = new MutableLiveData<>();
    private final MutableLiveData<Boolean> finishQuiz = new MutableLiveData<>();

    private final GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor;
    private final UpdateQuestionAndGameInteractor updateQuestionInteractor;
    private final PointsInteractor pointsInteractor;

    private final StartRouter startRouter;

    private final List<Question> questionList = new ArrayList<>();
    private Question toUpdate;
    private boolean started;

    QuestionViewModel(GetSingleGameAndQuestionsInteractor getSingleGameAndQuestionsInteractor,
                      UpdateQuestionAndGameInteractor updateQuestionInteractor,
                      PointsInteractor pointsInteractor,
                      StartRouter startRouter) {
        this.getSingleGameAndQuestionsInteractor = getSingleGameAndQuestionsInteractor;
        this.updateQuestionInteractor = updateQuestionInteractor;
        this.pointsInteractor = pointsInteractor;
        this.startRouter = startRouter;
        started = false;
    }

    void prepare(int gameId){
        Timber.d("prepare");
        disposable = getSingleGameAndQuestionsInteractor.getGameQuestions(gameId)
                .doOnNext(gameQuestion -> Timber.d("getSingleGameAndQuestionsInteractor Triggered size: %s",gameQuestion.questions.size()))
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
                .doOnSubscribe(subscription -> Timber.d("getSingleGameAndQuestionsInteractor subscribed"))
                .subscribe();
    }

    private void updateList(GameQuestion gameQuestion){
        Timber.d("updateList before %s",gameQuestion.questions.size() );
        List<Question> sorted = gameQuestion.questions.stream()
                .filter(question2 -> question2.getAnswerChosen()==null)
                .collect(Collectors.toList());
        Timber.d("updateList after %s",sorted.size() );
        questionList.clear();
        questionList.addAll(sorted);
    }

    private void prepareShowIntro(Game game){
        Timber.d("prepareShowIntro %s",game.getGameId());
        String cat = game.getCategory();
        String diff = game.getDifficulty();
        Uri uri = Uri.parse(String.format("file:///android_asset/categories/%s.png", Category.getMap().get(game.getCategory())));
        int answered = game.getAnswered();
        IntroData data = new IntroData(cat,diff,uri, answered);
        showIntro.setValue(data);
    }

    void chooseAnswer(String picked){
        Timber.d("chooseAnswer %s",picked);
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
        Timber.d("updateQuestion");
        if(questionList.size()==0) {
            showEnd.setValue(true);
        }else{
            Question q = questionList.get(0);
            q.setNumber(String.format("%s",10-questionList.size()+1));
            question.setValue(questionList.get(0));
        }
    }

    void start(){
        Timber.d("start");
        if(questionList.size()==0){
            Timber.d("start Q list size 0");
            showEnd.setValue(true);
        }else{
            Timber.d("start Q list size >0");
            updateQuestion();
            startQuiz.setValue(true);
        }
    }

    void quitQuiz(){
        Timber.d("quitQuiz");
        Question q = question().getValue();
        if(q != null){
            q.setAnswerChosen(QuestionC.TIMEOUT_ANSWER);
            disposable = updateQuestionInteractor.updateQuestion(q)
                    .doOnComplete(() -> Timber.d("Question updated"))
                    .subscribe();
        }

    }

    void nextQuestion(){
        Timber.d("nextQuestion");
        if(question.getValue()!=null){
            disposable = updateQuestionInteractor.updateQuestion(question.getValue())
                    .doOnComplete(() -> Timber.d("Question updated %s",question.getValue()))
                    .subscribe();
        }
    }

    void getPoints(int id){
        Timber.d("getPoints %s",id);
        disposable = pointsInteractor.getPoints(id)
                .subscribe(this::onPoints,this::onError);
    }

    private void onPoints(FinishedGame fg) {
        Timber.d("onPoints %s",fg);
        finishedGame.setValue(fg);
    }

    void uploadScore(){
        Timber.d("uploadScore");
        if(game.getValue()!=null){
            Timber.d("uploadScore non null");
            disposable = pointsInteractor.finishGameAndUploadPoints(game.getValue().game.getGameId())
                    .subscribe(this::onGameEnded,this::onError);
        }
    }

    private void onGameEnded() {
        Timber.d("onGameEnded");
        finishQuiz.setValue(true);
    }

    void openStart(Context context){
        startRouter.open(context, true);
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

    MutableLiveData<FinishedGame> finishedGame() {
        return finishedGame;
    }

    MutableLiveData<Boolean> finishQuiz(){
        return finishQuiz;
    }

}
