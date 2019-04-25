package com.example.qwez.ui.start;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Timer;

import javax.inject.Inject;

public class StartActivity extends BaseActivity{

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

        AndroidInjection.inject(this);;

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestions);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);
        viewModel.gameData().observe(this, this::onGames);
        viewModel.user().observe(this, this::onUser);

        viewModel.getUser();

    }

    private void onUser(String s) {
        setTitle(s);
    }

    private void onGames(List<Game> games) {

    }

    private void onProgess(Boolean aBoolean) {

    }

    private void onError(ErrorCarrier errorCarrier) {

    }

    private void onQuestions(List<Question> questions) {

    }

    @OnClick(R.id.button_add_question)
    void onClick(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_meny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.start_menu_settings:
                viewModel.openSettings(this);
                break;
        }
        return true;
    }
}
