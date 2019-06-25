package com.example.qwez.ui.question;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.entity.FinishedGame;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import timber.log.Timber;

public class FinishFragment extends BaseFragment {

    private QuestionViewModel viewModel;

    @BindView(R.id.you_scored) TextView score;
    @BindView(R.id.finish_game) Button button;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(QuestionViewModel.class);
        viewModel.game().observe(this, this::onGame);
        viewModel.finishedGame().observe(this, this::onFinishedGame);
        viewModel.error().observe(this, this::onError);

    }

    private void onError(ErrorCarrier errorCarrier) {
        Timber.d("onError: %s",errorCarrier.getMessage());
    }

    private void onFinishedGame(FinishedGame finishedGame) {
        Timber.d("onFinishedGame");
        score.setText(String.format("You scored %s points",finishedGame.getScore()));
        button.setVisibility(View.VISIBLE);
    }

    private void onGame(GameQuestion gameQuestion) {
        Timber.d("onGame");
        viewModel.getPoints(gameQuestion.game.getGameId());
    }

    @OnClick(R.id.finish_game)
    public void click(){
        Timber.d("click finish_game");
        viewModel.uploadScore();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_finish;
    }

}
