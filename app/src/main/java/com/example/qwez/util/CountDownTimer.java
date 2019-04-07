package com.example.qwez.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class CountDownTimer {

    private TimeUnit timeUnit;
    private Long startValue;
    private Disposable disposable;

    public CountDownTimer(Long startValue, TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.startValue = startValue;
    }

    public abstract void onTick(long tickValue);

    public abstract void onFinish();

    public void start(){
        Observable
                .zip(
                        Observable.range(0, startValue.intValue()),
                        Observable.interval(1, timeUnit),
                        (integer, aLong) -> startValue - integer)
                .subscribeOn(Schedulers.computation())  //compution rather than io?
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        onTick(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        onFinish();
                    }
                });
    }

    public void cancel(){
        if(disposable!=null) disposable.dispose();
    }
}
