package com.example.qwez.util;

import com.example.qwez.repository.opentdb.entity.Question;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Transaction;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

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

        List<com.example.qwez.repository.local.Question> converted = QuestionConverter.toDatabase(questions);

        assertEquals(converted.size(),1 );
        com.example.qwez.repository.local.Question questionConverted = converted.get(0);
        assertEquals(questionConverted.correctAnswer, question.getCorrectAnswer());
        assertEquals(questionConverted.wrongAnswer1, question.getIncorrectAnswers().get(0));

    }
}