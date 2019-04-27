package com.example.qwez.di;

import com.example.qwez.repository.opentdb.OpenTDB;
import com.example.qwez.repository.opentdb.OpenTDBAPI;
import com.example.qwez.repository.opentdb.OpenTDBType;

import dagger.Module;
import dagger.Provides;

@Module
public class OpenTDBModule {

    /**
     * Get OpenTDB. Singleton
     * @param openTDBAPI Dagger provided
     * @return OpenTDB
     */
    @Provides
    @ApplicationScope
    OpenTDBType openTDBType(OpenTDBAPI openTDBAPI){
        return new OpenTDB(openTDBAPI);
    }

}
