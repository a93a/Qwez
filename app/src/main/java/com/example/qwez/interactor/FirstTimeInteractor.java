package com.example.qwez.interactor;

import com.example.qwez.repository.sharedpref.SharedPreferencesRepositoryType;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FirstTimeInteractor {

    private final SharedPreferencesRepositoryType sharedPreferencesRepositoryType;

    public FirstTimeInteractor(SharedPreferencesRepositoryType sharedPreferencesRepositoryType) {
        this.sharedPreferencesRepositoryType = sharedPreferencesRepositoryType;
    }

    public Single<Boolean> checkFirstTime(){
        return sharedPreferencesRepositoryType.getNotFirstTime()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable setNotFirstTime(){
        return sharedPreferencesRepositoryType.setNotFirstTime(true)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
