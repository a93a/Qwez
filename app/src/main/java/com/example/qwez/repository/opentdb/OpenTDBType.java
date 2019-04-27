package com.example.qwez.repository.opentdb;

import com.example.qwez.repository.opentdb.entity.Question;

import java.util.List;

import io.reactivex.Single;

/**
 * API interface
 */
public interface OpenTDBType {

    /**
     * Get Question from API
     */
    Single<List<Question>> getQuestionByCategory(int amount, int category, String difficulty, String type);

}
