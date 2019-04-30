package com.example.qwez.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper Class for converting between {@link com.example.qwez.repository.opentdb.entity.Question} and {@link com.example.qwez.repository.local.Question}
 */
public final class QuestionConverter {

    private QuestionConverter(){
        //private constructor to avoid instantiation
    }

    /**
     * Convert a List of {@link com.example.qwez.repository.opentdb.entity.Question} to a List of{@link com.example.qwez.repository.local.Question}
     * @param toConvert List of OPENTBD API Question(s) to convert
     * @return List of Local Database Question(s)
     */
    public static List<com.example.qwez.repository.local.Question> toDatabase(List<com.example.qwez.repository.opentdb.entity.Question> toConvert) {

        List<com.example.qwez.repository.local.Question> converted = new ArrayList<>();

        toConvert.forEach(question -> {
            String wA1 = question.getIncorrectAnswers().get(0);
            String wA2 = question.getIncorrectAnswers().get(1);
            String wA3 = question.getIncorrectAnswers().get(2);

            converted.add(new com.example.qwez.repository.local.Question(
                    question.getQuestion(),
                    question.getCorrectAnswer(),
                    wA1,
                    wA2,
                    wA3));
        });

        return converted;

    }
}
