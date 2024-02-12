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
import com.example.qwez.databinding.ActivityHighscoreBinding;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.entity.Highscore;
import com.example.qwez.ui.highscore.recyclerview.HighscoreAdapter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class HighscoreActivity extends BaseActivity<ActivityHighscoreBinding> {

    @Inject
    HighscoreVMFactory factory;
    HighscoreViewmodel viewmodel;

    private HighscoreAdapter adapter;

    /**
     * Create BaseActivity with {@code binding} layout binding
     *
     * @param binding the layout binding
     */
    public HighscoreActivity(ActivityHighscoreBinding binding) {
        super(binding);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_highscore;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableDisplayHomeAsUp();


        adapter = new HighscoreAdapter();
        binding.highscoreRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.highscoreRecyclerview.setAdapter(adapter);

        binding.highscoreRecyclerview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //finished loading
                binding.highscoreRecyclerview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
            binding.highscoreRecyclerview.setVisibility(View.INVISIBLE);
            binding.loadingRecyclerview.setVisibility(View.VISIBLE);
        }else{
            binding.loadingRecyclerview.setVisibility(View.GONE);
            binding.highscoreRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    private void onHighscores(List<Highscore> highscores) {
        if(highscores.size() == 0){
            binding.highscoreRecyclerview.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }else{
            if(binding.noData.getVisibility() == View.VISIBLE){
                binding.noData.setVisibility(View.GONE);
                binding.highscoreRecyclerview.setVisibility(View.VISIBLE);
            }
            adapter.setData(highscores);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {

    }

    private void onHighscore(Integer integer) {
        Timber.d("BUZZ Gotten highscore: %s", integer);
        binding.yourActualScore.setText(String.format("%s", integer));
    }
}
