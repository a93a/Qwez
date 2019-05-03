package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import com.example.qwez.ui.question.QuestionActivity;
import com.example.qwez.util.Extras;

import timber.log.Timber;

/**
 * Opens QuestionActivity.class
 */
public class QuestionRouter {

    /**
     * Open QuestionActivity.class
     * @param context of current Activity
     * @param qId clear Activity stack. true clears stack
     */
    public void open(Context context, int qId){
        Timber.d("Game is %s", qId);
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(Extras.QUESTION_ID, qId);
        context.startActivity(intent);
    }

}
