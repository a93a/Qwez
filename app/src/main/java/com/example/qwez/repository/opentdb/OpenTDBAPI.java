package com.example.qwez.repository.opentdb;


import com.example.qwez.repository.opentdb.entity.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTDBAPI {

    /**
     * Get Question from API
     * @param amount of questions to get
     * @param category Question category
     * @param difficulty Question difficulty
     * @param type Question type
     * @return Retrofit Response with List of Question
     */
    @GET("api.php?")
    Observable<Response<ResponseBody>> getQuestions(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );

}
