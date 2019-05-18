package com.example.qwez.ui.highscore;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class HighscoreActivity extends BaseActivity {

    @Inject
    HighscoreVMFactory factory;
    HighscoreViewmodel viewmodel;

    @BindView(R.id.your_actual_score)
    TextView highScoreLabel;

    @Override
    protected int getLayout() {
        return R.layout.activity_highscore;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableDisplayHomeAsUp();

        viewmodel = ViewModelProviders.of(this, factory).get(HighscoreViewmodel.class);

        viewmodel.yourscore().observe(this, this::onHighscore);
        viewmodel.error().observe(this, this::onError);

        viewmodel.getUserHighscore();
        viewmodel.getHighscores();

    }

    private void onError(ErrorCarrier errorCarrier) {
    }

    private void onHighscore(Integer integer) {
        highScoreLabel.setText(String.format("%s", integer));
    }
}
