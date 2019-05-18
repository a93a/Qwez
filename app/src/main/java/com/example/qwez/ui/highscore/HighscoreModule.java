package com.example.qwez.ui.highscore;

import com.example.qwez.interactor.GetHighscoreInteractor;
import com.example.qwez.repository.firebase.FirebaseAuthRepositoryType;
import com.example.qwez.repository.firebase.FirebaseDatabaseType;

import dagger.Module;
import dagger.Provides;

@Module
public class HighscoreModule {

    @Provides
    GetHighscoreInteractor getUserHighscoreInteractor(FirebaseDatabaseType firebaseDatabaseType,
                                                      FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new GetHighscoreInteractor(firebaseDatabaseType, firebaseAuthRepositoryType);
    }

    @Provides
    HighscoreVMFactory highscoreVMFactory(GetHighscoreInteractor getUserHighscoreInteractor){
        return new HighscoreVMFactory(getUserHighscoreInteractor);
    }

}
