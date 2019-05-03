package com.example.qwez;

import org.junit.rules.ExternalResource;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxResources extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }
}
