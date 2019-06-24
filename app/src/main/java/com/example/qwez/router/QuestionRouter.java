package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.qwez.ui.question.QuestionActivity;
import com.example.qwez.util.ExtrasConstant;

/**
 * Opens QuestionActivity.class
 */
public class QuestionRouter {

    /**
     * Open QuestionActivity.class
     * @param context of current Activity
     * @param qId clear Activity stack. true clears stack
     */
    public void open(@NonNull Context context, int qId){
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(ExtrasConstant.QUESTION_ID, qId);
        context.startActivity(intent);
    }

}
