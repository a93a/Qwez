package com.example.qwez.ui.question;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.repository.local.entity.GameQuestion;
import com.example.qwez.repository.local.entity.Question;

public class FinishFragment extends BaseFragment {

    QuestionViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(QuestionViewModel.class);
        viewModel.game().observe(this, this::onGame);

    }

    private void onGame(GameQuestion gameQuestion) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_finish;
    }

}
