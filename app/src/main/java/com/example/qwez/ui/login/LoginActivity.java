package com.example.qwez.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivityWithFragment;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.LoginEvent;
import com.example.qwez.bus.event.SignupEvent;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.ui.dialog.CustomMaterialDialog;

import javax.inject.Inject;

public class LoginActivity extends BaseActivityWithFragment {

    @Inject
    LoginVMFactory factory;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideToolbar();

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);

        viewModel.progress().observe(this, this::onProgress);
        viewModel.error().observe(this,this::onError);
        viewModel.login().observe(this, this::onLogIn);
        viewModel.singup().observe(this, this::onSignup);

        replaceFragment(new LoginFragment(), R.id.frag_container, false);

    }

    @Override
    protected void onStart() {
        super.onStart();

        RxBus.subscribe(RxBus.SHOW_SIGN_UP_FRAGMENT, this, object -> {
            replaceFragment(new SignupFragment(),R.id.frag_container,true);
        });

        RxBus.subscribe(RxBus.TRY_LOG_IN, this, o -> {
            LoginEvent loginEvent = (LoginEvent) o;
            viewModel.logInUser(loginEvent.getEmail(), loginEvent.getPassword());
        });

        RxBus.subscribe(RxBus.TRY_SIGN_UP, this, o -> {
            SignupEvent signupEvent = (SignupEvent) o;
            viewModel.signupUser(signupEvent.getEmail(),signupEvent.getNick(),signupEvent.getPassword());
        });

    }

    private void onSignup(Boolean aBoolean) {
        viewModel.openStart(this, true);
    }

    private void onLogIn(Boolean success) {
        viewModel.openStart(this,true);
    }

    private void onError(ErrorCarrier error) {
        showCustomDialog(CustomMaterialDialog.error("Error",this, "Something went wrong: "+error.message+". Please try again"));
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

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

}
