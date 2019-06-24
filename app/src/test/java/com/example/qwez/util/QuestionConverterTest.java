package com.example.qwez.util;

import com.example.qwez.repository.opentdb.entity.Question;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionConverterTest {

    @Test
    public void toDatabase() {

        List<Question> questions = new ArrayList<>();
        List<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add("2");
        wrongAnswers.add("3");
        wrongAnswers.add("4");

        Question question = new Question("categ", "type", "diff", "1?", "1", wrongAnswers);
        questions.add(question);

        List<com.example.qwez.repository.local.entity.Question> converted = QuestionUtil.toDatabase(questions);

        assertEquals(converted.size(),1 );
        com.example.qwez.repository.local.entity.Question questionConverted = converted.get(0);
        assertEquals(questionConverted.getCorrectAnswer(), question.getCorrectAnswer());
        assertEquals(questionConverted.getWrongAnswer1(), question.getIncorrectAnswers().get(0));

    }
}