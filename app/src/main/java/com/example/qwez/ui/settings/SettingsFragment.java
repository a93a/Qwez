package com.example.qwez.ui.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.qwez.R;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.ChangeNickEvent;
import com.example.qwez.bus.event.ChangePassowordEvent;
import com.example.qwez.bus.event.ChangePhotoEvent;
import com.example.qwez.bus.event.LogoutEvent;
import com.example.qwez.util.ViewUtil;

public class SettingsFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final Preference logout = findPreference("log_out_preference");
        if (logout != null) {
            logout.setOnPreferenceClickListener(preference -> {
                ViewUtil.disablePreferenceShort(logout);
                RxBus.publish(RxBus.TRY_LOG_OUT, new LogoutEvent());
                return true;
            });
        }

        final Preference changePass = findPreference("change_password_preference");
        if (changePass != null) {
            changePass.setOnPreferenceClickListener(preference -> {
                ViewUtil.disablePreferenceShort(changePass);
                RxBus.publish(RxBus.TRY_CHANGE_PASSWORD, new ChangePassowordEvent(null));
                return true;
            });
        }

        final Preference changenick = findPreference("change_username_preference");
        if (changenick != null) {
            changenick.setOnPreferenceClickListener(preference -> {
               ViewUtil.disablePreferenceShort(changenick);
                RxBus.publish(RxBus.TRY_CHANGE_NICK, new ChangeNickEvent());
                return true;
            });
        }

        final Preference changePhoto = findPreference("change_user_photo");
        if(changePhoto != null ) {
            changePhoto.setOnPreferenceClickListener(preference -> {
                ViewUtil.disablePreferenceShort(changePhoto);
                RxBus.publish(RxBus.TRY_CHANGE_PHOTO, new ChangePhotoEvent());
                return true;
            });
        }

    }

}
