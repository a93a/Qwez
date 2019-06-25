package com.example.qwez.ui.question;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.entity.Answer;
import com.example.qwez.repository.local.entity.Question;
import com.example.qwez.util.CountDownTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class QuestionsFragment extends BaseFragment{

    private static final int PROGRESSBAR_MAX_VALUE = 100;
    private static final long QUESTION_TIME = 25;
    private static final int TIMER_SCALE = 100/(int)QUESTION_TIME;
    private static final int PROGRESSBAR_MIN_VALUE = 0;
    private static final String QUESTION_NUMBER_FORMAT = "%s/10";
    private static final CharSequence TIMEOUT_TEXT = "Time run out! Next";

    private QuestionViewModel viewModel;
    private List<String> shuffledAnswers = new ArrayList<>();
    private boolean canAnswer = false;
    private CountDownTimer timer;

    @BindColor(R.color.colorProgressGreenDark) int greenProgressColor;
    @BindColor(R.color.colorProgressYellow) int yellowProgressColor;
    @BindColor(R.color.colorProgressRed) int redProgressColor;
    @BindColor(R.color.colorProgressYellowLight) int yellowProgressColorLight;

    @BindView(R.id.textView_which_question) TextView whichQ;
    @BindView(R.id.textview_question) TextView questionView;
    @BindView(R.id.textview_answer_one) TextView ans1;
    @BindView(R.id.textview_answer_two) TextView ans2;
    @BindView(R.id.textview_answer_three) TextView ans3;
    @BindView(R.id.textview_answer_four) TextView ans4;
    @BindView(R.id.button_next) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(QuestionViewModel.class);
        viewModel.question().observe(this, this::onQuestion);
        viewModel.answer().observe(this, this::onAnswer);
        viewModel.timeoutAnswer().observe(this, this::onTimeout);

        Timber.d("Viewmodel: %s", viewModel.toString());

    }

    private void onTimeout(String s) {
        Timber.d("onTimeout");
        progressBar.setVisibility(View.INVISIBLE);
        button.setText(TIMEOUT_TEXT);
        button.setVisibility(View.VISIBLE);
        ans1.setBackgroundColor(yellowProgressColorLight);
        ans2.setBackgroundColor(yellowProgressColorLight);
        ans3.setBackgroundColor(yellowProgressColorLight);
        ans4.setBackgroundColor(yellowProgressColorLight);
    }

    private void onAnswer(Answer answer) {
        Timber.d("onAnswer");
        progressBar.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
        setAnswerViewColor(ans1, answer);
        setAnswerViewColor(ans2, answer);
        setAnswerViewColor(ans3, answer);
        setAnswerViewColor(ans4, answer);
    }

    private void setAnswerViewColor(TextView ans, Answer answer){
        String viewText = ans.getText().toString();
        if(viewText.equals(answer.getYourAnswer())){
            if(viewText.equals(answer.getCorrectAnswer())){
                ans.setBackgroundColor(context.getColor(R.color.colorProgressGreenLight));
            }else{
                ans.setBackgroundColor(context.getColor(R.color.colorProgressRedLight));
            }
        }else if((viewText.equals(answer.getCorrectAnswer()) && !viewText.equals(answer.getYourAnswer()))){
            ans.setBackgroundColor(context.getColor(R.color.colorProgressYellowLight));
        }
    }

    private void onQuestion(Question question) {

        Timber.d("onQuestion");

        canAnswer = true;

        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(PROGRESSBAR_MAX_VALUE);
        setProgressColor(greenProgressColor);

        questionView.setText(question.getQuestion());

        shuffledAnswers.clear();

        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);

        shuffledAnswers.add(question.getCorrectAnswer());
        shuffledAnswers.add(question.getWrongAnswer1());
        shuffledAnswers.add(question.getWrongAnswer2());
        shuffledAnswers.add(question.getWrongAnswer3());

        Collections.shuffle(shuffledAnswers);

        ans1.setText(shuffledAnswers.get(0));
        ans2.setText(shuffledAnswers.get(1));
        ans3.setText(shuffledAnswers.get(2));
        ans4.setText(shuffledAnswers.get(3));

        whichQ.setText(String.format(QUESTION_NUMBER_FORMAT, question.getNumber()));

        timer = new CountDownTimer(QUESTION_TIME, TimeUnit.SECONDS) {
            @Override
            public void onTick(long tickValue) {
                if(progressBar!=null){
                    progressBar.setProgress((int)tickValue * TIMER_SCALE);
                    if(progressBar.getProgress() >= 50){
                        setProgressColor(greenProgressColor);
                    }else if(progressBar.getProgress() >= 25){
                        setProgressColor(yellowProgressColor);
                    }else{
                        setProgressColor(redProgressColor);
                    }
                }
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                if(progressBar!=null){
                    progressBar.setProgress(PROGRESSBAR_MIN_VALUE);
                    setProgressColor(redProgressColor);
                }
                if(canAnswer){
                    disposeOfTimer();
                    viewModel.chooseAnswer(null);
                }
            }
        };
        timer.start();
    }

    private void setProgressColor(@ColorInt int color){
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private void onAnswerClick(TextView ans){
        Timber.d("onAnswerClick");
        if(canAnswer){
            disposeOfTimer();
            viewModel.chooseAnswer(ans.getText().toString());
            canAnswer = false;
        }
    }

    @OnClick({R.id.textview_answer_one,
            R.id.textview_answer_two,
            R.id.textview_answer_three,
            R.id.textview_answer_four})
    public void onClick(TextView textView){
        onAnswerClick(textView);
    }

    @OnClick(R.id.button_next)
    public void onClickButton(){
        viewModel.nextQuestion();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_question;
    }

    @Override
    public void onDestroyView() {
        disposeOfTimer();
        super.onDestroyView();
    }

    private void disposeOfTimer(){
        Timber.d("disposeOfTimer");
        if(timer != null){
            timer.cancel();
        }
    }
}
