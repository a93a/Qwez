package com.example.qwez.ui.splash;

import android.os.Bundle;
import android.util.Log;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.GameQuestion;
import com.example.qwez.router.StartRouter;
import com.example.qwez.ui.start.StartVMFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Insert;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    @Inject
    SplashVMFactory factory;
    SplashViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideToolbar();

        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.error().observe(this, this::onError);
        viewModel.user().observe(this, this::onUser);

        viewModel.getUser();
    }

    private void onUser(FirebaseUser firebaseUser) {
        Timber.d("onUser");
        if(firebaseUser != null){
            viewModel.openStart(this);
        }else{
            viewModel.openLogin(this);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {
    }

    private void onProgress(Boolean aBoolean) {
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_splash;
    }
}
