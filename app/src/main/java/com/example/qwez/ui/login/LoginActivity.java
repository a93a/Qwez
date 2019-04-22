package com.example.qwez.ui.login;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.BooleanEvent;
import com.example.qwez.bus.RxBus;

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

        hideSystemUI();

        AndroidInjection.inject(this);

        setContentView(R.layout.layout_login);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);

        replaceFragment(new LoginFragment(), R.id.frag_container, false);

        RxBus.subscribe(RxBus.SIGNUP_ALREADY_ACCOUNT_EVENT, this, object -> {
            replaceFragment(new SignupFragment(),R.id.frag_container,true);
        });

    }

    private void replaceFragment(Fragment fragment, @IdRes int id, boolean addToStack){
        if(addToStack){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, fragment)
                    .addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, fragment)
                    .commit();
        }
    }

}
