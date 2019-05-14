package com.example.qwez.repository.sharedpref;

import android.content.SharedPreferences;

import com.example.qwez.repository.sharedpref.rxwrapper.SharedPrefsWrapper;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Sharedpreferences repository
 */
public class SharedPreferencesRepository implements SharedPreferencesRepositoryType {

    private final SharedPreferences prefs;

    private static final String FIRST_TIME = "first time";
    private static final String REMEMBER = "remember";
    private static final String IS_REMEMBER = "is remember";

    public SharedPreferencesRepository(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public Single<Boolean> getNotFirstTime() {
        return Single.fromCallable(() -> prefs.getBoolean(FIRST_TIME, false))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setNotFirstTime(boolean setTo) {
        return Completable.fromAction( () -> prefs.edit().putBoolean(FIRST_TIME, setTo).apply())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> isRemembered() {
        return Single.fromCallable(() -> prefs.getBoolean(IS_REMEMBER, false))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<String> getRemembered() {
        return Single.fromCallable(() -> prefs.getString(REMEMBER, null))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setRemembered(String toRemember) {
        return Completable.fromAction(() -> prefs.edit().putString(REMEMBER, toRemember).apply())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setNotRemember() {
        return Completable.fromAction(() -> prefs.edit().remove(REMEMBER).apply())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setIsRemember(boolean remember) {
        return Completable.fromAction(() -> prefs.edit().putBoolean(IS_REMEMBER, remember).apply());
    }

}
