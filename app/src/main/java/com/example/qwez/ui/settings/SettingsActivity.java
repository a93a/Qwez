package com.example.qwez.ui.settings;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.databinding.ActivitySettingsBinding;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.validator.PasswordValidate;
import com.google.android.material.textfield.TextInputLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import timber.log.Timber;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding> {

    @Inject
    SettingsVMFactory factory;
    SettingsViewModel viewModel;

    private final RxPermissions rxPermissions = new RxPermissions(this);
    private static final int READ_REQUEST_CODE = 42;

    /**
     * Create BaseActivity with {@code binding} layout binding
     *
     * @param binding the layout binding
     */
    public SettingsActivity(ActivitySettingsBinding binding) {
        super(binding);
    }

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
        viewModel.accountDelete().observe(this, this::onAccountDeleted);

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

    private void onAccountDeleted(Boolean aBoolean) {
        viewModel.startLogin(this, true);
    }

    private void onPhotoChaned(Boolean changed) {
        if(changed){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Photo changed!",this);
            showCustomDialog(builder);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {
        MaterialDialog.Builder builder = CustomMaterialDialog.error("Error", this, errorCarrier.getMessage());
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
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        viewModel.logoutUser();
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.TRY_CHANGE_PASSWORD, this, o -> {
            LinearLayout layout = getLayoutForDialog(R.layout.dialog_change_pass,R.id.change_pass_linear);

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
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        });


        RxBus.subscribe(RxBus.TRY_DELETE_ACCOUNT, this, o -> {
            LinearLayout layout = getLayoutForDialog(R.layout.dialog_confirm_pass, R.id.dialog_conf_pass_layout);
            TextView passText = layout.findViewById(R.id.edit_dialog_conf_pass);

            MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Confirm deletion", this, layout)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        String pass = passText.getText().toString();
                        Timber.d("HELLO SIR %s",pass);
                        viewModel.deleteAccount(pass);
                    });
            showCustomDialog(builder);
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

    private LinearLayout getLayoutForDialog(@LayoutRes int layout, @IdRes int root){
        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(layout, null);
        return stdView.findViewById(root);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

}
