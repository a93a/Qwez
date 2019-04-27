package com.example.qwez.ui.settings;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.ui.dialog.CustomMaterialDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SettingsActivity extends BaseActivity {

    @Inject
    SettingsVMFactory factory;
    SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        setContentView(R.layout.layout_settings);

        enableDisplayHomeAsUp();

        //setTitle(R.string.settings);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this,factory).get(SettingsViewModel.class);
        viewModel.logout().observe(this, this::onLogoutSuccess);
        viewModel.progress().observe(this, this::onProgress);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        RxBus.subscribe(RxBus.TRY_LOG_OUT, this, o -> {
            MaterialDialog.Builder builder = CustomMaterialDialog.areYouSure("Are you sure?", this)
                    .onNegative((dialog, which) -> {
                        dialog.dismiss();
                    })
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        viewModel.logoutUser();
                    });
            showCustomDialog(builder);
        });

    }

    private void onProgress(Boolean progress) {
        if(progress){
            MaterialDialog.Builder mDialog = CustomMaterialDialog.loading("Loading...", this)
                    .onNegative((dialog, which) -> {
                        viewModel.cancel();
                        dialog.dismiss();
                    });
            showCustomDialog(mDialog);
        }
    }

    private void onLogoutSuccess(Boolean aBoolean) {
        if(aBoolean){
            viewModel.startLogin(this, true);
        }
    }


}
