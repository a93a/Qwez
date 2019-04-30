package com.example.qwez.ui.question;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.repository.local.Question;
import com.example.qwez.util.CountDownTimer;
import com.example.qwez.util.Extras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

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
        showQuestion();
    }

    private void showQuestion(){
        if(questions.size() > count){
            Question current = questions.get(count);
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

            progressBar.setProgress(100);


            count++;
        }else{
            finish();
        }
    }

    @OnClick(R.id.textview_question)
    public void onClick(){
        showQuestion();
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_question;
    }

}
