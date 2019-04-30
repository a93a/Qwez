package com.example.qwez.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.qwez.repository.sharedpref.rxwrapper.SharedPrefsWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    @Provides
    @ApplicationScope
    SharedPrefsWrapper sharedPrefsWrapper(SharedPreferences sharedPreferences){
        return SharedPrefsWrapper.createSharedPrefsWrapper(sharedPreferences);
    }

    @Provides
    @ApplicationScope
    SharedPreferences sharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
