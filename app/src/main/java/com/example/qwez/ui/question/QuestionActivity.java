package com.example.qwez.ui.question;

import android.os.Bundle;
import android.util.Log;

import com.example.qwez.base.BaseActivity;
import com.example.qwez.repository.entity.Question;
import com.example.qwez.util.Extras;

import java.util.List;

import androidx.annotation.Nullable;

public class QuestionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Question> questions = (List<Question>) getIntent().getSerializableExtra(Extras.QUESTION_LIST);
        questions.forEach(question -> Log.d(QuestionActivity.class.getName(), question.toString()));

    }
}
