package com.example.qwez.ui.question;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.repository.local.Question;
import com.example.qwez.util.Extras;
import com.example.qwez.util.ViewColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionActivity extends BaseActivity{

    @Inject
    QuestionVMFactory factory;
    QuestionViewModel viewModel;

    @BindView(R.id.textView_which_question) TextView whichQ;
    @BindView(R.id.textview_question) TextView questionView;
    @BindView(R.id.textview_answer_one) TextView ans1;
    @BindView(R.id.textview_answer_two) TextView ans2;
    @BindView(R.id.textview_answer_three) TextView ans3;
    @BindView(R.id.textview_answer_four) TextView ans4;
    @BindView(R.id.button_next) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private Question question;
    private static final String TIME_RUN_OUT = "timeout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this,factory).get(QuestionViewModel.class);
        viewModel.question().observe(this, this::onQuestion);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int qId = bundle.getInt(Extras.QUESTION_ID);
            viewModel.getQuestions(qId);
        }

    }

    private List<String> randomizeAnswers(Question question){
        List<String> a = new ArrayList<>();
        a.add(question.getCorrectAnswer());
        a.add(question.getWrongAnswer1());
        a.add(question.getWrongAnswer2());
        a.add(question.getWrongAnswer3());
        Collections.shuffle(a);
        return a;
    }

    private void onQuestion(Question question) {
        this.question = question;
        showNext(false);
        List<String> al = randomizeAnswers(question);
        displayQuestions(question.getQuestion(), al);
        enableClicks(true);
    }

    private void enableClicks(boolean enable){
        ans1.setClickable(enable);
        ans2.setClickable(enable);
        ans3.setClickable(enable);
        ans4.setClickable(enable);
    }

    private void showNext(boolean show){
        if(show){
            progressBar.setVisibility(View.INVISIBLE);
            button.setVisibility(View.VISIBLE);
        }else{
            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void setTimerProgress(int progress){
        progressBar.setProgress(progress);
    }

    private void onAnswerClick(String aClicked){
        if(aClicked.equals(TIME_RUN_OUT)){
            //do something
            return;
        }
    }

    private void displayQuestions(String question, List<String> answers){
        questionView.setText(question);
        ans1.setText(answers.get(0));
        ans2.setText(answers.get(1));
        ans3.setText(answers.get(2));
        ans4.setText(answers.get(3));
    }

    private void clearAnswers(){
        setViewColor(ans1, ViewColor.WHITE);
        setViewColor(ans2, ViewColor.WHITE);
        setViewColor(ans3, ViewColor.WHITE);
        setViewColor(ans4, ViewColor.WHITE);
    }

    private void setViewColor(View view, ViewColor viewColor){
        switch (viewColor){
            case RED:
                view.setBackgroundColor(getColor(R.color.colorProgressRed));
                break;
            case GREEN:
                view.setBackgroundColor(getColor(R.color.colorProgressGreenDark));
                break;
            case YELLOW:
                view.setBackgroundColor(getColor(R.color.colorProgressYellow));
                break;
            case WHITE:
                view.setBackgroundColor(Color.WHITE);
                break;
                default:
                    break;
        }
    }

    @OnClick(R.id.textview_answer_one)
    public void onA1(){
        onAnswerClick(ans1.getText().toString());
    }

    @OnClick(R.id.textview_answer_two)
    public void onA2(){
        onAnswerClick(ans2.getText().toString());
    }

    @OnClick(R.id.textview_answer_three)
    public void onA3(){
        onAnswerClick(ans3.getText().toString());
    }

    @OnClick(R.id.textview_answer_four)
    public void onA4(){
        onAnswerClick(ans4.getText().toString());
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_question;
    }

}
