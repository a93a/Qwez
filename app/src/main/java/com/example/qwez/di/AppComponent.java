package com.example.qwez.di;

import com.example.qwez.App;
import com.example.qwez.ui.highscore.HighscoreModule;
import com.example.qwez.ui.login.LoginFragmentModule;
import com.example.qwez.ui.login.LoginModule;
import com.example.qwez.ui.question.QuestionModule;
import com.example.qwez.ui.settings.SettingsModule;
import com.example.qwez.ui.splash.SplashModule;
import com.example.qwez.ui.start.StartModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Dagger Component
 */
@ApplicationScope
@Component(modules = {AndroidInjectionModule.class,
        FirebaseModule.class,
        NetworkModule.class,
        StartModule.class,
        BinderModule.class,
        OpenTDBModule.class,
        ApplicationModule.class,
        LocalDatabaseModule.class,
        SplashModule.class,
        LoginModule.class,
        SettingsModule.class,
        QuestionModule.class,
        SharedPreferencesModule.class,
        LoginFragmentModule.class,
        HighscoreModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);
        AppComponent build();
    }

    void inject(App app);

}

