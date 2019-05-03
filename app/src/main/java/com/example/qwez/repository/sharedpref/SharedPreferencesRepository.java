package com.example.qwez.repository.sharedpref;

import com.example.qwez.repository.sharedpref.rxwrapper.SharedPrefsWrapper;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Sharedpreferences repository
 */
public class SharedPreferencesRepository implements SharedPreferencesRepositoryType {

    private final SharedPrefsWrapper sharedPrefsWrapper;
    private static final String FIRST_TIME = "first time";

    public SharedPreferencesRepository(SharedPrefsWrapper sharedPrefsWrapper) {
        this.sharedPrefsWrapper = sharedPrefsWrapper;
    }

    @Override
    public Single<Boolean> getNotFirstTime() {
        return Single.fromObservable(sharedPrefsWrapper
                        .getBoolean(FIRST_TIME)
                        .getObserver())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setNotFirstTime(boolean setTo) {
        return Completable.fromObservable(observer -> sharedPrefsWrapper.getBoolean(FIRST_TIME).set(setTo))
                .subscribeOn(Schedulers.io());
    }

}
