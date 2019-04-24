package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.ui.question.QuestionActivity;
import com.example.qwez.util.Extras;

import java.io.Serializable;
import java.util.List;

public class QuestionRouter {

    public void open(Context context){
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }

}
