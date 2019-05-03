package com.example.qwez.ui.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.ChangeNickEvent;
import com.example.qwez.bus.event.ChangePassowordEvent;
import com.example.qwez.bus.event.LogoutEvent;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.util.RxOperators;
import com.example.qwez.validator.PasswordValidate;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SettingsFragment extends PreferenceFragmentCompat{

    private Preference logout;
    private Preference changePass;
    private Preference changenick;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        logout = findPreference("log_out_preference");
        logout.setOnPreferenceClickListener(preference -> {
            disable(logout);
            RxBus.publish(RxBus.TRY_LOG_OUT, new LogoutEvent());
            return true;
        });

        changePass = findPreference("change_password_preference");
        changePass.setOnPreferenceClickListener(preference -> {
            disable(changePass);
            RxBus.publish(RxBus.TRY_CHANGE_PASSWORD, new ChangePassowordEvent(null));
            return true;
        });

        changenick = findPreference("change_username_preference");
        changenick.setOnPreferenceClickListener(preference -> {
            disable(changenick);
            RxBus.publish(RxBus.TRY_CHANGE_NICK, new ChangeNickEvent());
            return true;
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("CheckResult")
    private void disable(Preference preference){
        preference.setEnabled(false);
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> preference.setEnabled(true));
    }

}
