package com.example.qwez.entity;

public class Answer {

    private String yourAnswer;
    private String correctAnswer;

    public Answer(String yourAnswer, String correctAnswer) {
        this.yourAnswer = yourAnswer;
        this.correctAnswer = correctAnswer;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
