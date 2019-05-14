package com.example.qwez.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.qwez.repository.sharedpref.SharedPreferencesRepository;
import com.example.qwez.repository.sharedpref.SharedPreferencesRepositoryType;
import com.example.qwez.repository.sharedpref.rxwrapper.SharedPrefsWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    private static final String SHARED_PREFS = "shared prefs qwez";

    @Provides
    @ApplicationScope
    SharedPreferencesRepositoryType sharedPreferencesRepositoryType(SharedPreferences sharedPreferences){
        return new SharedPreferencesRepository(sharedPreferences);
    }

    @Provides
    @ApplicationScope
    SharedPrefsWrapper sharedPrefsWrapper(SharedPreferences sharedPreferences){
        return SharedPrefsWrapper.createSharedPrefsWrapper(sharedPreferences);
    }

    @Provides
    @ApplicationScope
    SharedPreferences sharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

}
