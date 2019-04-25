package com.example.qwez.bus;

import android.util.SparseArray;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public final class RxBus {

    private static RxBus INSTANCE;


    //SparseArray more optimal when keys are primitives (Compared to hashmap (Android specific class))
    private static SparseArray<PublishSubject<Object>> subjectMap = new SparseArray<>();
    private static Map<Object, CompositeDisposable> subscriptionsMap = new HashMap<>();

    public static final int SHOW_LOGIN_FRAGMENT = 0;
    public static final int SHOW_SIGN_UP_FRAGMENT = 1;
    public static final int SHOW_QUESTION_ACTIVITY = 2;
    public static final int TRY_LOG_IN = 3;
    public static final int TRY_SIGN_UP = 4;
    public static final int TRY_LOG_OUT = 5;

    @Retention(SOURCE)
    @IntDef({SHOW_LOGIN_FRAGMENT,
            SHOW_SIGN_UP_FRAGMENT,
            SHOW_QUESTION_ACTIVITY,
            TRY_LOG_IN,
            TRY_SIGN_UP,
            TRY_LOG_OUT})
    @interface Subject {
    }

    private RxBus() {
    }

    public static synchronized RxBus get() {
        if (INSTANCE == null) {
            INSTANCE = new RxBus();
        }
        return INSTANCE;
    }

    @NonNull
    private static PublishSubject<Object> getSubject(@Subject int subjectCode) {
        PublishSubject<Object> subject = subjectMap.get(subjectCode);
        if (subject == null) {
            subject = PublishSubject.create();
            subject.subscribeOn(AndroidSchedulers.mainThread());
            subjectMap.put(subjectCode, subject);
        }

        return subject;
    }

    @NonNull
    private static CompositeDisposable getCompositeDisposable(@NonNull Object object) {
        CompositeDisposable compositeDisposable = subscriptionsMap.get(object);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            subscriptionsMap.put(object, compositeDisposable);
        }

        return compositeDisposable;
    }

    public static void subscribe(@Subject int subject, @NonNull Object lifecycle, @NonNull Consumer<Object> action) {
        Disposable disposable = getSubject(subject).subscribe(action);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    public static void unregister(@NonNull Object lifecycle) {
        CompositeDisposable compositeDisposable = subscriptionsMap.remove(lifecycle);
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public static void publish(@Subject int subject, @NonNull Object message) {
        getSubject(subject).onNext(message);
    }

}
