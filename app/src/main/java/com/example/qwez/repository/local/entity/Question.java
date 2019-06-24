package com.example.qwez.repository.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Entity for Question
 *
 * Is it worth to index this entity?
 */
@Entity(foreignKeys = @ForeignKey(entity = Game.class,
        parentColumns = "id",childColumns = "question_id",
        onDelete = ForeignKey.CASCADE),
        tableName = "questions")
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "question_id")
    public int qId;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "correct_answer")
    private String correctAnswer;

    @ColumnInfo(name = "wrong_answer_one")
    private String wrongAnswer1;

    @ColumnInfo(name = "wrong_answer_two")
    private String wrongAnswer2;

    @ColumnInfo(name = "wrong_answer_three")
    private String wrongAnswer3;

    @ColumnInfo(name = "answer_chosen")
    private String answerChosen;

    @Ignore
    private String number;

    public Question(String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerChosen() {
        return answerChosen;
    }

    public void setAnswerChosen(String answerChosen) {
        this.answerChosen = answerChosen;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
