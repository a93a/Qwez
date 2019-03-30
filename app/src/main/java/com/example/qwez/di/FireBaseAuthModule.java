package com.example.qwez.di;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Provides FireBaseAuth
 */
@Module
public class FireBaseAuthModule {

    @Provides
    @ApplicationScope
    FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }

}
