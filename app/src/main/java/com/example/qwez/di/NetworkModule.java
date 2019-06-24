package com.example.qwez.di;

import com.example.qwez.repository.opentdb.OpenTDBAPI;
import com.example.qwez.util.UrlConstant;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    /**
     * Get OpenTDBAPI. Singleton
     * @param retrofit Dagger provided
     * @return OpenTDBAPI
     */
    @Provides
    @ApplicationScope
    OpenTDBAPI openTDBAPI(Retrofit retrofit){
        return retrofit.create(OpenTDBAPI.class);
    }

    /**
     * Get Retrofit. Singleton
     * @param okHttpClient Dagger provided
     * @param gson Dagger provided
     * @return Retrofit
     */
    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(UrlConstant.URL_END_POINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();
    }

    /**
     *Get OkHttpClient. Singleton
     * @param httpLoggingInterceptor Dagger provided
     * @return OkHttpClient
     */
    @Provides
    @ApplicationScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    /**
     * Get HttpLoggingInterceptor. Singleton
     * @return HttpLoggingInterceptor
     */
    @Provides
    @ApplicationScope
    HttpLoggingInterceptor httpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * Get Gson. Singleton
     * @return Gson
     */
    @Provides
    @ApplicationScope
    Gson gson(){
        return new Gson();
    }

}
