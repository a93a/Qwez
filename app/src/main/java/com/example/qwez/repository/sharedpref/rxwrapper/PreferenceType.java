package com.example.qwez.repository.sharedpref.rxwrapper;

import io.reactivex.Observable;

public interface PreferenceType<T> {

    String key();

    T get();

    void set(T value);

    boolean isSet();

    void delete();

    Observable<T> getObserver();

}
