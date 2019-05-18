package com.example.qwez.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.BooleanEvent;
import com.example.qwez.bus.event.LoginEvent;
import com.example.qwez.interactor.RememberUserInteractor;
import com.example.qwez.validator.EmailValidate;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;


public class LoginFragment extends BaseFragment {

    @Inject
    RememberUserInteractor interactor;

    private Disposable disposable;

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
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.login_load_progress)
    ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_log_or_sign;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        disposable = interactor.getRememberIfExists()
                .subscribe(this::onRemembered, this::onError);

    }

    private void onError(Throwable error){

    }

    private void onRemembered(String remembered){
        email.setText(remembered);
        checkBox.setChecked(true);
    }

    @OnClick(R.id.button_signup)
    void signupClick(){
        RxBus.publish(RxBus.SHOW_SIGN_UP_FRAGMENT, new BooleanEvent(true));
    }

    @OnClick(R.id.button_login)
    void loginClick(){
        String sEmail = email.getText().toString().trim();
        String sPass = password.getText().toString();
        boolean rememberMe = checkBox.isChecked();
        if(validateForm(sEmail,sPass)){
            Action action = () -> RxBus.publish(RxBus.TRY_LOG_IN, new LoginEvent(sEmail, sPass, rememberMe));
            if(rememberMe){
                disposable = interactor.setRemembered(sEmail)
                        .subscribe(action);
            }else{
                disposable = interactor.dontRemember()
                        .subscribe(action);
            }
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

    @Override
    public void onStop() {
        super.onStop();
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
