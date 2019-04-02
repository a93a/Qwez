package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import com.example.qwez.repository.entity.Question;
import com.example.qwez.ui.question.QuestionActivity;
import com.example.qwez.util.Extras;

import java.io.Serializable;
import java.util.List;

public class QuestionRouter {

    public void open(Context context, List<Question> questions){
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(Extras.QUESTION_LIST, (Serializable) questions);
        context.startActivity(intent);
    }

}
