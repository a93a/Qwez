package com.example.qwez.di;

import com.example.qwez.App;
import com.example.qwez.ui.start.StartModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component(modules = {AndroidInjectionModule.class,
        FireBaseAuthModule.class,
        NetworkModule.class,
        StartModule.class,
        BinderModule.class,
        OpenTDBModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);
        AppComponent build();
    }

    void inject(App app);

}

