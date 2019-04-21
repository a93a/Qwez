package com.example.qwez.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class LoginActivity extends BaseActivity {

    @Inject
    LoginVMFactory factory;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        setContentView(R.layout.layout_login);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);

    }

}
