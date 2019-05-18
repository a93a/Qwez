package com.example.qwez.ui.question;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivityWithFragment;
import com.example.qwez.repository.local.Question;
import com.example.qwez.util.Extras;

import javax.inject.Inject;

public class QuestionActivity extends BaseActivityWithFragment {

    @Inject
    QuestionVMFactory factory;
    QuestionViewModel viewModel;

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

    private void onQuestion(Question question) {

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_question;
    }

}
