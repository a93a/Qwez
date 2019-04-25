package com.example.qwez.ui.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.qwez.R;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.LogoutEvent;

import timber.log.Timber;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final Preference logout = findPreference("log_out_preference");
        logout.setOnPreferenceClickListener(preference -> {
            RxBus.publish(RxBus.TRY_LOG_OUT, new LogoutEvent());
            return true;
        });

    }


}
