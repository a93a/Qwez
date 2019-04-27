package com.example.qwez.di;

import com.example.qwez.repository.firebase.FirebaseAuthRepository;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Provides FireBaseAuth
 */
@Module
public class FireBaseAuthModule {

    /**
     * Get FirebaseAuthRepository. Singleton.
     * @param firebaseAuth Dagger Provided
     * @return FirebaseAuthRepository
     */
    @Provides
    @ApplicationScope
    FirebaseAuthRepositoryType firebaseAuthRepositoryType(FirebaseAuth firebaseAuth){
        return new FirebaseAuthRepository(firebaseAuth);
    }

    /**
     * Get FirebaseAuth. Singleton
     * @return FirebaseAuth
     */
    @Provides
    @ApplicationScope
    FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }

}
