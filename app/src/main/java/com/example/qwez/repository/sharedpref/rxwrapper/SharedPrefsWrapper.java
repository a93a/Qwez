package com.example.qwez.repository.sharedpref.rxwrapper;

import androidx.annotation.NonNull;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.SharedPreferences;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

/**
 * Class that adds a RxJava wrapper to SharedPreferences
 */
public final class SharedPrefsWrapper {

    private final SharedPreferences sharedPreferences;
    private final Observable<String> sharedPrefsChanges;

    public static SharedPrefsWrapper createSharedPrefsWrapper(@NonNull SharedPreferences sharedPreferences){
        return new SharedPrefsWrapper(sharedPreferences);
    }

    private SharedPrefsWrapper(final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.sharedPrefsChanges = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                final OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        emitter.onNext(key);
                    }
                };
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
                    }
                });
                sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
            }
        }).share(); //share() is a shorthand for publish().refcount(), making share() an RxJava Subject. This means
        // you can have multiple subscribers for the same Observable.
    }

    public PreferenceType<Boolean> getBoolean(String key){
        return new Preference<>(sharedPreferences, key, BooleanPreference.INSTANCE, sharedPrefsChanges);
    }

    public PreferenceType<String> getString(String key){
        return new Preference<>(sharedPreferences, key, StringPreference.INSTANCE, sharedPrefsChanges);
    }

    public PreferenceType<Integer> getInteger(String key){
        return new Preference<>(sharedPreferences, key, IntegerPreference.INSTANCE, sharedPrefsChanges);
    }


}