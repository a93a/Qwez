package com.example.qwez.di;

import com.example.qwez.ui.login.LoginActivity;
import com.example.qwez.ui.login.LoginModule;
import com.example.qwez.ui.question.QuestionActivity;
import com.example.qwez.ui.question.QuestionModule;
import com.example.qwez.ui.settings.SettingsActivity;
import com.example.qwez.ui.settings.SettingsModule;
import com.example.qwez.ui.splash.SplashActivity;
import com.example.qwez.ui.splash.SplashModule;
import com.example.qwez.ui.start.StartActivity;
import com.example.qwez.ui.start.StartModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Bind all Activity package Module(s) to their Activity-
 */
@Module
public abstract class BinderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = StartModule.class)
    abstract StartActivity bindStartActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsActivity bindSettingsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = QuestionModule.class)
    abstract QuestionActivity bindQuestionActivity();

}
