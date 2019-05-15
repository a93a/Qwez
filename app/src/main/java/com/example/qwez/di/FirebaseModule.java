package com.example.qwez.di;

import com.example.qwez.repository.firebase.FirebaseAuthRepository;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseStorageRepository;
import com.example.qwez.repository.firebase.FirebaseStorageRepositoryType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dagger.Module;
import dagger.Provides;

/**
 * Provides FireBaseAuth
 */
@Module
public class FirebaseModule {

    @Provides
    @ApplicationScope
    FirebaseStorageRepositoryType firebaseStorageRepositoryType(StorageReference storageReference){
        return new FirebaseStorageRepository(storageReference);
    }

    @Provides
    @ApplicationScope
    StorageReference storageReference(FirebaseStorage firebaseStorage){
        return firebaseStorage.getReference();
    }

    @Provides
    @ApplicationScope
    FirebaseStorage firebaseStorage(){
        return FirebaseStorage.getInstance();
    }

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
