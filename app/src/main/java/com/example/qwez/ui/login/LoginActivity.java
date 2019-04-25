package com.example.qwez.ui.login;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.base.BaseActivityFragment;
import com.example.qwez.bus.event.LoginEvent;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.SignupEvent;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.ui.dialog.CustomMaterialDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.example.qwez.ui.dialog.CustomMaterialDialog.loading;

public class LoginActivity extends BaseActivityFragment {

    @Inject
    LoginVMFactory factory;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        setContentView(R.layout.layout_login);

        hideToolbar();

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);

        viewModel.progress().observe(this, this::onProgress);
        viewModel.error().observe(this,this::onError);
        viewModel.login().observe(this, this::onLogIn);
        viewModel.singup().observe(this, this::onSignup);

        replaceFragment(new LoginFragment(), R.id.frag_container, false);

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

}