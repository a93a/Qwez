package com.example.qwez.ui.start;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.Question;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

public class StartActivity extends BaseActivity implements View.OnClickListener{

    @Inject
    StartVMFactory factory;
    StartViewModel viewModel;

    @BindView(R.id.button_add_question)
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_start);

        ButterKnife.bind(this);

        button.setOnClickListener(this);

        AndroidInjection.inject(this);

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestions);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);
        viewModel.gameData().observe(this, this::onGames);

    }

    private void onGames(List<Game> games) {
        Log.d("GAMES::::::::::::::::::::::::::::::::::::::::", "Total games: "+games.size());
    }

    private void onProgess(Boolean aBoolean) {

    }

    private void onError(ErrorCarrier errorCarrier) {
        Log.e(StartActivity.class.getName(), errorCarrier.message);
    }

    private void onQuestions(List<Question> questions) {
        //new QuestionRouter().open(this, questions);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add_question:
                viewModel.getQuestion(Category.FILMS, Difficulty.EASY);
                break;
        }
    }
}
