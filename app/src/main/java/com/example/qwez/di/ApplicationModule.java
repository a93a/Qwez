package com.example.qwez.di;

import android.content.Context;

import com.example.qwez.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    Context context(App app){
        return app.getApplicationContext();
    }

}
