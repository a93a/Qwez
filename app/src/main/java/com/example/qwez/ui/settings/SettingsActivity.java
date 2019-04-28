package com.example.qwez.ui.settings;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.ChangePassowordEvent;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.validator.PasswordValidate;
import com.google.android.material.textfield.TextInputLayout;

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

        enableDisplayHomeAsUp();

        //setTitle(R.string.settings);

        viewModel = ViewModelProviders.of(this,factory).get(SettingsViewModel.class);
        viewModel.logout().observe(this, this::onLogoutSuccess);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.passChange().observe(this, this::onPasswordChange);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new SettingsFragment())
                .commit();
    }

    private void onPasswordChange(Boolean changed) {
        if(changed){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Password changed successful!", this)
                    .onPositive((dialog, which) -> dialog.dismiss());
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

            TextView pass1 = layout.findViewById(R.id.dialog_cp_pass_one);
            TextView pass2 = layout.findViewById(R.id.dialog_cp_pass_two);
            TextInputLayout input1 = layout.findViewById(R.id.dialog_cp_input_one);
            TextInputLayout input2 = layout.findViewById(R.id.dialog_cp_input_two);

            MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Change your password", this, layout)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onNegative((dialog, which) -> {
                        dialog.dismiss();
                        String pass1text = pass1.getText().toString();
                        String pass2text = pass2.getText().toString();
                        PasswordValidate passwordValidate = new PasswordValidate();
                        if(passwordValidate.isValid(pass1text)){
                            if(pass1text.equals(pass2text)){
                                viewModel.changePassword(pass1text);
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
