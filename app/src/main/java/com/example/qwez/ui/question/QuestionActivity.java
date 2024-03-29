package com.example.qwez.ui.question;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.databinding.ActivitySettingsBinding;
import com.example.qwez.entity.FinishedGame;
import com.example.qwez.entity.IntroData;
import com.example.qwez.util.ExtrasConstant;

import javax.inject.Inject;

import timber.log.Timber;

public class QuestionActivity extends BaseActivity<ActivitySettingsBinding> {

    @Inject
    QuestionVMFactory factory;
    QuestionViewModel viewModel;

    /**
     * Create BaseActivity with {@code binding} layout binding
     *
     * @param binding the layout binding
     */
    public QuestionActivity(ActivitySettingsBinding binding) {
        super(binding);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideToolbar();

        viewModel = ViewModelProviders.of(this,factory).get(QuestionViewModel.class);
        viewModel.showIntro().observe(this, this::onShowIntro);
        viewModel.showEnd().observe(this, this::onShowEnd);
        viewModel.startQuiz().observe(this, this::onStartQuiz);
        viewModel.finishQuiz().observe(this, this::onQuizFinished);

        Timber.d("Viewmodel: %s", viewModel.toString());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int qId = bundle.getInt(ExtrasConstant.QUESTION_ID);
            viewModel.prepare(qId);
        }

    }

    private void onQuizFinished(Boolean finished) {
        if(finished){
            viewModel.openStart(this);
        }
    }

    private void onStartQuiz(Boolean start) {
        if(start){
            QuestionsFragment questionsFragment = new QuestionsFragment();
            replaceFragment(questionsFragment, R.id.question_container, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxBus.subscribe(RxBus.START_OR_END_QUIZ, this, o -> {
            viewModel.start();
        });
    }

    private void onShowEnd(Boolean show) {
        if(show){
            replaceFragment(new FinishFragment(), R.id.question_container, false);
        }
    }

    private void onShowIntro(IntroData data) {
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntroFragment.INTRO_DATA, data);
        fragment.setArguments(bundle);

        replaceFragment(fragment, R.id.question_container, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.quitQuiz();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_question;
    }

}
