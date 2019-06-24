package com.example.qwez.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * A RxJava implementation of a countdown timer.
 * Using RxJava to handle this helps us to perform this task
 * in the background thread easily.
 */
public abstract class CountDownTimer {

    private final TimeUnit timeUnit;  //unit to count in
    private final Long startValue;    //how many ticks
    private Disposable disposable;  //disposable for the operation

    /**
     *
     * @param startValue amount of intervals for the countdown
     * @param timeUnit Timeunit to use as the interval for each countdown
     */
    public CountDownTimer(Long startValue, TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.startValue = startValue;
    }

    /**
     * Gets called each time the countdown timer intervals
     * @param tickValue the current tick interval
     */
    public abstract void onTick(long tickValue);

    /**
     * Gets called once the countdown timer has finished. This means the object has
     * completed the iteration (Objected iterated {@param startValue} times using
     * {@param timeUnit as interval unit})
     */
    public abstract void onFinish();

    /**
     * Start the countdown iteration.
     */
    public void start(){
        Timber.d("NEW TIMER");
        Observable
                .zip(   //zip Observable.range and Observable.interval.
                        // Wait for 1 emission from each
                        Observable.range(0, startValue.intValue()), //take 1
                        Observable.interval(1, timeUnit),   //take 1
                        (integer, aLong) -> startValue - integer)   //decrease startvalue by 1 interval unit
                .subscribeOn(Schedulers.computation())  //Do the calculation on the computation scheduler
                .observeOn(AndroidSchedulers.mainThread())  //standard
                .subscribe(new Observer<Long>() {   //subscribe with a new observer
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Timber.d("TICK %S", aLong);
                        onTick(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //idk what to do here?
                    }

                    @Override
                    public void onComplete() {
                        onFinish();
                    }
                });
    }

    /**
     * Cancel the countdown iteration
     */
    public void cancel(){
        if(disposable!=null) {
            disposable.dispose();
            Timber.d("TIMER CANCELLED");
        }
    }
}
