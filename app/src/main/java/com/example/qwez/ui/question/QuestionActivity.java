package com.example.qwez.ui.question;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.Question;
import com.example.qwez.util.Extras;

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
    @BindView(R.id.textview_answer_one) TextView question1;
    @BindView(R.id.textview_answer_two) TextView question2;
    @BindView(R.id.textview_answer_three) TextView question3;
    @BindView(R.id.textview_answer_four) TextView question4;
    @BindView(R.id.button_next) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private List<Question> questions = new ArrayList<>();
    private int qId;
    private int count = 0;
    private boolean nextQuestion = false;
    private Question current = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this,factory).get(QuestionViewModel.class);
        viewModel.questions().observe(this, this::onQuestions);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            qId = bundle.getInt(Extras.QUESTION_ID);
            viewModel.getQuestions(qId);
        }

    }

    private void onQuestions(GameQuestion gameQuestion) {
        questions.clear();
        questions.addAll(gameQuestion.questions);
        runQuiz();
    }

    private void runQuiz(){
        if(questions.size() > count) {
            question1.setBackgroundColor(Color.WHITE);
            question2.setBackgroundColor(Color.WHITE);
            question3.setBackgroundColor(Color.WHITE);
            question4.setBackgroundColor(Color.WHITE);
            current = questions.get(count);
            displayQ();
            count++;
        }else{
            finish();
        }
    }

    private void displayQ(){
        questionView.setText(current.getQuestion());

        List<String> qlist = new ArrayList<>();
        qlist.add(current.getWrongAnswer1());
        qlist.add(current.getWrongAnswer2());
        qlist.add(current.getWrongAnswer3());
        qlist.add(current.getCorrectAnswer());
        Collections.shuffle(qlist);
        question1.setText(qlist.get(0));
        question2.setText(qlist.get(1));
        question3.setText(qlist.get(2));
        question4.setText(qlist.get(3));
    }

    private void checkResults(String chosen){
        new Handler().postDelayed(() -> { }, 3000);
        String corr = current.getCorrectAnswer();
        if(chosen.equals(corr)){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(() -> { }, 1000);
        runQuiz();
    }

    @OnClick(R.id.textview_answer_one)
    public void onA1(){
        question1.setBackgroundColor(getColor(R.color.colorThird));
        checkResults(question1.getText().toString());
    }

    @OnClick(R.id.textview_answer_two)
    public void onA2(){
        question2.setBackgroundColor(getColor(R.color.colorThird));
        checkResults(question2.getText().toString());
    }

    @OnClick(R.id.textview_answer_three)
    public void onA3(){
        question3.setBackgroundColor(getColor(R.color.colorThird));
        checkResults(question3.getText().toString());
    }

    @OnClick(R.id.textview_answer_four)
    public void onA4(){
        question4.setBackgroundColor(getColor(R.color.colorThird));
        checkResults(question4.getText().toString());
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_question;
    }

}
