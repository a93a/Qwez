package com.example.qwez.repository.sharedpref.rxwrapper;

import android.content.SharedPreferences;

public final class BooleanPreference implements Preference.Type<Boolean>{

    public static final BooleanPreference INSTANCE = new BooleanPreference();

    @Override
    public Boolean get(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void set(SharedPreferences.Editor editor, String key, Boolean value) {
        editor.putBoolean(key, value);
    }

}
