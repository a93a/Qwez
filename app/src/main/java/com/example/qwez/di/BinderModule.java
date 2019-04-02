package com.example.qwez.di;

import com.example.qwez.ui.start.StartActivity;
import com.example.qwez.ui.start.StartModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BinderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = StartModule.class)
    abstract StartActivity bindQuestionActivity();


}
