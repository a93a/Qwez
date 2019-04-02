package com.example.qwez.ui.start;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.entity.Question;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;

import java.util.List;

import javax.inject.Inject;

public class StartActivity extends BaseActivity implements View.OnClickListener{

    @Inject
    StartVMFactory factory;
    StartViewModel viewModel;

    @BindView(R.id.spinner_category) Spinner category;
    @BindView(R.id.spinner_difficulty) Spinner difficulty;
    @BindView(R.id.button_start) Button start;
    @BindView(R.id.progressBar_loadquestion) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start);

        ButterKnife.bind(this);

        start.setOnClickListener(this);

        category.setAdapter(new ArrayAdapter<Category>(
                this,
                android.R.layout.simple_spinner_item,
                Category.values()
        ));
        difficulty.setAdapter(new ArrayAdapter<Difficulty>(
                this,
                android.R.layout.simple_spinner_item,
                Difficulty.values()
        ));

        AndroidInjection.inject(this);

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestions);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);

    }

    private void onProgess(Boolean aBoolean) {
        if(aBoolean)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void onError(ErrorCarrier errorCarrier) {
        start.setEnabled(true);
        Log.e(StartActivity.class.getName(), errorCarrier.message);
    }

    private void onQuestions(List<Question> questions) {
        start.setEnabled(true);
        new QuestionRouter().open(this, questions);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start:
                start.setEnabled(false);
                viewModel.getQuestion((Category) category.getSelectedItem(), (Difficulty) difficulty.getSelectedItem());
                break;
        }
    }
}
