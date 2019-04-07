package com.example.qwez.ui.splash;

import android.os.Bundle;
import android.util.Log;

import com.example.qwez.base.BaseActivity;
import com.example.qwez.router.StartRouter;

import androidx.annotation.Nullable;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StartRouter().open(this, true)
        ;
    }
}
