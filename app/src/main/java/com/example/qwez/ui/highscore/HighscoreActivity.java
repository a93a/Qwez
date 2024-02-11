package com.example.qwez.ui.highscore;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.entity.Highscore;
import com.example.qwez.ui.highscore.recyclerview.HighscoreAdapter;

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
    @BindView(R.id.highscore_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_recyclerview)
    ProgressBar progressBar;
    @BindView(R.id.no_data)
    TextView emptylist;

    private HighscoreAdapter adapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_highscore;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableDisplayHomeAsUp();

        adapter = new HighscoreAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //finished loading
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        viewmodel = ViewModelProviders.of(this, factory).get(HighscoreViewmodel.class);

        viewmodel.yourscore().observe(this, this::onHighscore);
        viewmodel.highscore().observe(this, this::onHighscores);
        viewmodel.error().observe(this, this::onError);
        viewmodel.progress().observe(this, this::onProgress);

        viewmodel.getUserHighscore();
        viewmodel.getHighscores();

    }

    private void onProgress(Boolean progress) {
        if(progress){
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void onHighscores(List<Highscore> highscores) {
        if(highscores.size() == 0){
            recyclerView.setVisibility(View.GONE);
            emptylist.setVisibility(View.VISIBLE);
        }else{
            if(emptylist.getVisibility() == View.VISIBLE){
                emptylist.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            adapter.setData(highscores);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {

    }

    private void onHighscore(Integer integer) {
        Timber.d("BUZZ Gotten highscore: %s", integer);
        highScoreLabel.setText(String.format("%s", integer));
    }
}
