package com.example.qwez.repository;

import com.example.qwez.repository.entity.Question;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.QuestionType;

import java.util.List;

import io.reactivex.Single;

public interface OpenTDBType {

    Single<List<Question>> getQuestionByCategory(int amount, Category category, Difficulty difficulty, QuestionType type);

}