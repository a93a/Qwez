package com.example.qwez.ui.question;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.Extras;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.textView_which_question) TextView whichQ;
    @BindView(R.id.textview_question) TextView questionView;
    @BindView(R.id.textview_answer_one) TextView question1;
    @BindView(R.id.textview_answer_two) TextView question2;
    @BindView(R.id.textview_answer_three) TextView question3;
    @BindView(R.id.textview_answer_four) TextView question4;
    @BindView(R.id.button_next) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private List<Question> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_question);

        ButterKnife.bind(this);

        question1.setOnClickListener(this);
        question2.setOnClickListener(this);
        question3.setOnClickListener(this);
        question4.setOnClickListener(this);
        button.setOnClickListener(this);

        questions = (List<Question>) getIntent().getSerializableExtra(Extras.QUESTION_LIST);

    }

    @Override
    public void onClick(View v) {

    }

}
