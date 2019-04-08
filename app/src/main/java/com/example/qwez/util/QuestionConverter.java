package com.example.qwez.util;

import java.util.ArrayList;
import java.util.List;

public class QuestionConverter {

    public static List<com.example.qwez.repository.local.Question> toDatabase(List<com.example.qwez.repository.opentdb.entity.Question> toConvert, int id) {

        List<com.example.qwez.repository.local.Question> converted = new ArrayList<>();

        toConvert.forEach(question -> {
            String wA1 = question.getIncorrectAnswers().get(0);
            String wA2 = question.getIncorrectAnswers().get(1);
            String wA3 = question.getIncorrectAnswers().get(2);

            converted.add(new com.example.qwez.repository.local.Question(
                    id,
                    question.getQuestion(),
                    question.getCorrectAnswer(),
                    wA1,
                    wA2,
                    wA3));
        });

        return converted;

    }
}
