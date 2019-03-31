package com.example.qwez.repository;


import com.example.qwez.repository.entity.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTDBAPI {

    @GET("api.php?")
    Observable<Response<ResponseBody>> getQuestions(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );

}
