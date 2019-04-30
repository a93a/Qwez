package com.example.qwez.repository.sharedpref.rxwrapper;

import android.content.SharedPreferences;

public final class StringPreference implements Preference.Type<String> {

    public static final StringPreference INSTANCE = new StringPreference();

    @Override
    public String get(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getString(key, null);
    }

    @Override
    public void set(SharedPreferences.Editor editor, String key, String value) {
        editor.putString(key, value);
    }

}
