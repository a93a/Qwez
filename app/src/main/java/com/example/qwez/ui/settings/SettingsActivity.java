package com.example.qwez.ui.settings;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.ChangeNickEvent;
import com.example.qwez.bus.event.ChangePhotoEvent;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.validator.PasswordValidate;
import com.google.android.material.textfield.TextInputLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import javax.inject.Inject;

import timber.log.Timber;

public class SettingsActivity extends BaseActivity {

    @Inject
    SettingsVMFactory factory;
    SettingsViewModel viewModel;

    private final RxPermissions rxPermissions = new RxPermissions(this);
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableDisplayHomeAsUp();

        //setTitle(R.string.settings);

        viewModel = ViewModelProviders.of(this,factory).get(SettingsViewModel.class);
        viewModel.logout().observe(this, this::onLogoutSuccess);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.passChange().observe(this, this::onPasswordChange);
        viewModel.nickChange().observe(this, this::onNickChanged);
        viewModel.photoChange().observe(this, this::onPhotoChaned);

        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if(granted){
                        //do nothing
                    }else{
                        MaterialDialog.Builder builder = CustomMaterialDialog
                                .okWithText("Permission needed", this,"Permission is needed for settings to properly work. Without it you can not change user profile photo.");
                        showCustomDialog(builder);
                    }
                });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new SettingsFragment())
                .commit();
    }

    private void onPhotoChaned(Boolean changed) {
        if(changed){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Photo changed!",this);
            showCustomDialog(builder);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {
        MaterialDialog.Builder builder = CustomMaterialDialog.error("Error", this, errorCarrier.message);
        showCustomDialog(builder);
    }

    private void onNickChanged(Boolean changed) {
        if(changed){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Nickname changed.", this);
            showCustomDialog(builder);
        }
    }

    private void onPasswordChange(Boolean changed) {
        if(changed){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Password changed successful!", this)
                    .onPositive((dialog, which) -> dialog.dismiss());
            showCustomDialog(builder);
        }
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

        RxBus.subscribe(RxBus.TRY_CHANGE_PASSWORD, this, o -> {
            LayoutInflater factory = LayoutInflater.from(this);
            final View stdView = factory.inflate(R.layout.dialog_change_pass, null);
            LinearLayout layout = stdView.findViewById(R.id.change_pass_linear);

            TextView oldpass = layout.findViewById(R.id.dialog_cp_pass_old);
            TextView pass1 = layout.findViewById(R.id.dialog_cp_pass_one);
            TextView pass2 = layout.findViewById(R.id.dialog_cp_pass_two);
            TextInputLayout input1 = layout.findViewById(R.id.dialog_cp_input_one);
            TextInputLayout input2 = layout.findViewById(R.id.dialog_cp_input_two);

            MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Change your password", this, layout)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {

                        dialog.dismiss();
                        String oldPass = oldpass.getText().toString();
                        String pass1text = pass1.getText().toString();
                        String pass2text = pass2.getText().toString();
                        PasswordValidate passwordValidate = new PasswordValidate();
                        if(passwordValidate.isValid(pass1text)){
                            if(pass1text.equals(pass2text)){
                                viewModel.changePassword(oldPass,pass1text);
                            }else{
                                input1.setError("Passwords does not equal");
                                input2.setError("Passwords does not equal");
                            }
                        }else{
                            input1.setError(passwordValidate.getErrorMessage());
                            input2.setError(passwordValidate.getErrorMessage());
                        }
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.TRY_CHANGE_NICK, this, o -> {
            ChangeNickEvent changeNickEvent = (ChangeNickEvent) o;
            MaterialDialog.Builder builder = CustomMaterialDialog.emptyDialog("Change nickname", this)
                    .input("new nickname", null, (dialog, input) -> {})
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        String nick = dialog.getInputEditText().getText().toString();
                        viewModel.changeNick(nick);
                        dialog.dismiss();
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.TRY_CHANGE_PHOTO, this, o -> {
            ChangePhotoEvent c = (ChangePhotoEvent) o;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                if(uri != null){
                    viewModel.changePhoto(uri);
                }
            }
        }
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

    @Override
    protected int getLayout() {
        return R.layout.layout_settings;
    }

}
