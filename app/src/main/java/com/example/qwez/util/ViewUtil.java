package com.example.qwez.util;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;

import androidx.preference.Preference;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Class with View Utils (Includes Preference)
 */
public class ViewUtil {

    private static final int SHORT_DURATION_MILLISECONDS = 500;

    public static void disableMenuItemShort(MenuItem item){
        disableMenuItem(item, SHORT_DURATION_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    public static void disableMenuItem(MenuItem item, int duration, TimeUnit timeUnit){
        item.setEnabled(false);
        getTimer(duration, timeUnit).subscribe(o -> item.setEnabled(true));
    }

    /**
     * Disable a Preference for a short amount of time (500 Milliseconds)
     * @param preference to disable
     */
    public static void disablePreferenceShort(Preference preference){
        disablePreference(preference, SHORT_DURATION_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    /**
     * Disable a Preference
     * @param preference to disable
     * @param duration duration in Timeunit
     * @param timeUnit to use as duration
     */
    @SuppressLint("CheckResult")
    public static void disablePreference(Preference preference, int duration, TimeUnit timeUnit){
        preference.setEnabled(false);
        getTimer(duration, timeUnit)
                .subscribe(o -> preference.setEnabled(true));
    }

    /**
     * Disable a View for a short amount of time (500 Milliseconds)
     * @param view to disable
     */
    public static void disableViewShort(View view){
        disableView(view, SHORT_DURATION_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    /**
     * Disable a View
     * @param view to disable
     * @param duration duration in Timeunit
     * @param timeUnit to use as duration
     */
    @SuppressLint("CheckResult")
    public static void disableView(View view, int duration, TimeUnit timeUnit){
        view.setEnabled(false);
        getTimer(duration, timeUnit)
                .subscribe(o -> view.setEnabled(true));
    }

    /**
     * @return "timer" with duration of timeunit
     */
    private static Observable getTimer(int duration, TimeUnit timeUnit){
        return  Observable.timer(duration, timeUnit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
