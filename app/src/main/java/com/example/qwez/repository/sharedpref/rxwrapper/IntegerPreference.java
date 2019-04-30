package com.example.qwez.repository.sharedpref.rxwrapper;

import android.content.SharedPreferences;

public final class IntegerPreference implements Preference.Type<Integer>{

    public static final IntegerPreference INSTANCE = new IntegerPreference();

    @Override
    public Integer get(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getInt(key, 0);
    }

    @Override
    public void set(SharedPreferences.Editor editor, String key, Integer value) {
        editor.putInt(key, value);
    }

}
