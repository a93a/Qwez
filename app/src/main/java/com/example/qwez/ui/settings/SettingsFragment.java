package com.example.qwez.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.ChangePassowordEvent;
import com.example.qwez.bus.event.LogoutEvent;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.validator.PasswordValidate;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final Preference logout = findPreference("log_out_preference");
        logout.setOnPreferenceClickListener(preference -> {
            RxBus.publish(RxBus.TRY_LOG_OUT, new LogoutEvent());
            return true;
        });

        final Preference changePass = findPreference("change_password_preference");
        changePass.setOnPreferenceClickListener(preference -> {
            RxBus.publish(RxBus.TRY_CHANGE_PASSWORD, new ChangePassowordEvent(null));
            return true;
        });

    }


}
