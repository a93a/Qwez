package com.example.qwez.ui.question;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    @Provides
    QuestionVMFactory questionVMFactory(){
        return new QuestionVMFactory();
    }

}
