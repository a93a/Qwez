package com.example.qwez.interactor;

import com.example.qwez.repository.sharedpref.SharedPreferencesRepositoryType;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class RememberUserInteractor {

    private final SharedPreferencesRepositoryType sharedPreferencesRepositoryType;

    public RememberUserInteractor(SharedPreferencesRepositoryType sharedPreferencesRepositoryType) {
        this.sharedPreferencesRepositoryType = sharedPreferencesRepositoryType;
    }

    public Completable setRemembered(String remember){
        return sharedPreferencesRepositoryType.setRemembered(remember)
                .andThen( sharedPreferencesRepositoryType.setIsRemember(true))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<String> getRememberIfExists(){
        return sharedPreferencesRepositoryType.isRemembered()
                .flatMapMaybe(aBoolean -> {
                    if(aBoolean){
                        return sharedPreferencesRepositoryType.getRemembered()
                                .flatMapMaybe(Maybe::just);
                    }else{
                        return Maybe.empty();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable dontRemember(){
        return sharedPreferencesRepositoryType.setIsRemember(false)
                .andThen(sharedPreferencesRepositoryType.setNotRemember())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
