package com.example.qwez.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.BooleanEvent;
import com.example.qwez.bus.event.LoginEvent;
import com.example.qwez.validator.EmailValidate;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;


public class LoginFragment extends BaseFragment {

    private static final int SHORTEST_ALLOWED_PASSWORD_LENGTH = 8;

    @BindView(R.id.editText_email)
    EditText email;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.editText_password)
    EditText password;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.button_signup)
    Button signup;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.textView_signup)
    TextView bottomText;

    private Observable<CharSequence> emailObservable;
    private Observable<CharSequence> passObservable;
    private DisposableObserver<Boolean> subscriber;

    @Override
    protected int getLayout() {
        return R.layout.fragment_log_or_sign;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.button_signup)
    void signupClick(){
        RxBus.publish(RxBus.SHOW_SIGN_UP_FRAGMENT, new BooleanEvent(true));
    }

    @OnClick(R.id.button_login)
    void loginClick(){
        String sEmail = email.getText().toString().trim();
        String sPass = password.getText().toString();
        if(validateForm(sEmail,sPass)){
            RxBus.publish(RxBus.TRY_LOG_IN, new LoginEvent(sEmail,sPass));
        }
    }

    private boolean validateForm(String email, String pass){
        EmailValidate emailValidate = new EmailValidate();
        boolean emailValid = emailValidate.isValid(email);
        boolean passValid = pass.length() >= 8;
        if(!emailValid){
            emailLayout.setError(emailValidate.getErrorMessage());
        }else{
            emailLayout.setError(null);
        }
        if(!passValid){
            passwordLayout.setError("Not a valid password");
        }else{
            passwordLayout.setError(null);
        }
        return emailValid&&passValid;
    }

}
