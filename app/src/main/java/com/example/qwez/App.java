package com.example.qwez;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.qwez.di.AppComponent;
import com.example.qwez.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;
import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * Application Class
 */
public class App extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        //LeakCanary.install(this);

        Stetho.initializeWithDefaults(this);

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

    }

    /**
     * Checks whether network is available or not, returns true if network is available
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkAvailable = null;
        if (connectivityManager != null) {
            networkAvailable = connectivityManager.getActiveNetworkInfo();
        }
        return networkAvailable != null && networkAvailable.isConnectedOrConnecting();
    }

    /**
     * Checks whether Wifi network is available or not, returns true if Wifi network is available
     */
    public boolean isWifiNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkAvailable = null;
        if (connectivityManager != null) {
            networkAvailable = connectivityManager.getActiveNetworkInfo();
        }
        return networkAvailable != null && networkAvailable.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingActivityInjector;
    }
}
