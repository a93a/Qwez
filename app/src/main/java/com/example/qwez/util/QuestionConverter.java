package com.example.qwez.util;

import androidx.appcompat.app.WindowDecorActionBar;

import com.example.qwez.repository.sharedpref.rxwrapper.StringPreference;

import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

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
            String q = question.getQuestion();
            String qCA = question.getCorrectAnswer();
            String wA1 = question.getIncorrectAnswers().get(0);
            String wA2 = question.getIncorrectAnswers().get(1);
            String wA3 = question.getIncorrectAnswers().get(2);
            q = StringEscapeUtils.unescapeHtml4(q);
            qCA = StringEscapeUtils.unescapeHtml4(qCA);
            wA1 = StringEscapeUtils.unescapeHtml4(wA1);
            wA2 = StringEscapeUtils.unescapeHtml4(wA2);
            wA3 = StringEscapeUtils.unescapeHtml4(wA3);

            converted.add(new com.example.qwez.repository.local.Question(
                    q,
                    qCA,
                    wA1,
                    wA2,
                    wA3));
        });

        return converted;

    }
}
