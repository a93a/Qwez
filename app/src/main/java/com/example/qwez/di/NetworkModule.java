package com.example.qwez.di;

import com.example.qwez.repository.OpenTDBAPI;
import com.example.qwez.util.URL;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    OpenTDBAPI openTDBAPI(Retrofit retrofit){
        return retrofit.create(OpenTDBAPI.class);
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(URL.URL_END_POINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();
    }


    @Provides
    @ApplicationScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().addInterceptor(httpLoggingInterceptor);
        return new OkHttpClient();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor httpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @ApplicationScope
    Gson gson(){
        return new Gson();
    }

}
