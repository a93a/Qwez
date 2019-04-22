package com.example.qwez.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.bus.BooleanEvent;
import com.example.qwez.bus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.editText_email)
    EditText email;
    @BindView(R.id.editText_password)
    EditText password;
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.button_signup)
    Button signup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_log_or_sign, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_signup)
    void signupClick(){
        RxBus.publish(RxBus.SIGNUP_ALREADY_ACCOUNT_EVENT, new BooleanEvent(true));
    }

    @OnClick(R.id.button_login)
    void loginClick(){
    }

}
