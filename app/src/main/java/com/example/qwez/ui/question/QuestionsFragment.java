package com.example.qwez.ui.question;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.util.ViewColor;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionsFragment extends BaseFragment{

    @BindView(R.id.textView_which_question) TextView whichQ;
    @BindView(R.id.textview_question) TextView questionView;
    @BindView(R.id.textview_answer_one) TextView ans1;
    @BindView(R.id.textview_answer_two) TextView ans2;
    @BindView(R.id.textview_answer_three) TextView ans3;
    @BindView(R.id.textview_answer_four) TextView ans4;
    @BindView(R.id.button_next) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;


    @OnClick(R.id.textview_answer_one)
    public void onA1(){

    }

    @OnClick(R.id.textview_answer_two)
    public void onA2(){

    }

    @OnClick(R.id.textview_answer_three)
    public void onA3(){

    }

    @OnClick(R.id.textview_answer_four)
    public void onA4(){

    }

    private void setViewColor(View view, ViewColor viewColor){
        switch (viewColor){
            case RED:
                view.setBackgroundColor(context.getColor(R.color.colorProgressRed));
                break;
            case GREEN:
                view.setBackgroundColor(context.getColor(R.color.colorProgressGreenDark));
                break;
            case YELLOW:
                view.setBackgroundColor(context.getColor(R.color.colorProgressYellow));
                break;
            case WHITE:
                view.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
