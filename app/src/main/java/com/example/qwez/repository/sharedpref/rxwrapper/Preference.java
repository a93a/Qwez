package com.example.qwez.repository.sharedpref.rxwrapper;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import io.reactivex.Observable;

public final class Preference<T> implements PreferenceType<T> {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private final String key;
    private final Type<T> adapter;
    private final Observable<T> values;

    interface Type<T> {
        T get(SharedPreferences sharedPreferences, String key);
        void set(SharedPreferences.Editor editor, String key, T value);
    }

    @SuppressLint("CommitPrefEdits")
    public Preference(SharedPreferences preferences, String key, Type<T> adapter, Observable<String> values) {
        this.preferences = preferences;
        this.editor = preferences.edit();
        this.key = key;
        this.adapter = adapter;
        this.values = values
                .filter(key::equals)
                .map(s -> get());
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public T get() {
        return adapter.get(preferences, key);
    }

    @Override
    public void set(T value) {
        adapter.set(editor, key,value);
    }

    @Override
    public boolean isSet() {
        return preferences.contains(key);
    }

    @Override
    public void delete() {
        editor.remove(key).apply();
    }

    @Override
    public Observable<T> getObserver() {
        return values;
    }

}
