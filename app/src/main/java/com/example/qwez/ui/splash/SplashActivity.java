package com.example.qwez.ui.splash;

import android.os.Bundle;
import android.util.Log;

import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.router.StartRouter;
import com.example.qwez.ui.start.StartVMFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Insert;

public class SplashActivity extends BaseActivity {

    @Inject
    SplashVMFactory factory;
    SplashViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.error().observe(this, this::onError);

    }

    private void onError(ErrorCarrier errorCarrier) {

    }

    private void onProgress(Boolean aBoolean) {

    }

}
