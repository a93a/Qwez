package com.example.qwez.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.SignupEvent;
import com.example.qwez.validator.EmailValidate;
import com.example.qwez.validator.PasswordValidate;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupFragment extends BaseFragment {

    private static final String PASS_NOT_EQUAL = "Passwords does not match";
    @BindView(R.id.editText_email_signup)
    EditText email;
    @BindView(R.id.layout_email_signup)
    TextInputLayout layoutEmail;
    @BindView(R.id.editText_password_signup)
    EditText pass;
    @BindView(R.id.layout_pass_signup)
    TextInputLayout layoutPass;
    @BindView(R.id.editText_password_again_signup)
    EditText passAgain;
    @BindView(R.id.layout_pass_again_signup)
    TextInputLayout layoutPassAgain;
    @BindView(R.id.button_signup_signup)
    Button signin;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.button_signup_signup)
    void signUpClick(){
        String emailText = email.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = passAgain.getText().toString();
        if(validateForm(emailText,pass1,pass2)){
            RxBus.publish(RxBus.TRY_SIGN_UP, new SignupEvent(emailText,pass1));
        }
    }

    private boolean validateForm(String e, String p1, String p2){
        EmailValidate emailValidate = new EmailValidate();
        boolean validEmail = emailValidate.isValid(e);
        if (!validEmail){
            layoutEmail.setError(emailValidate.getErrorMessage());
            return false;
        }
        PasswordValidate passwordValidate = new PasswordValidate();
        boolean pass1valid = passwordValidate.isValid(p1);
        boolean passEqual = p1.equals(p2);
        if(pass1valid){
            if(!passEqual){
                layoutPassAgain.setError(PASS_NOT_EQUAL);
            }
        }else{
            layoutPass.setError(passwordValidate.getErrorMessage());
        }
        return validEmail && pass1valid && passEqual;
    }

}
