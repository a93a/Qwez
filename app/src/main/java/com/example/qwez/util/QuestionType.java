package com.example.qwez.util;

/**
 * Question type ENUM
 */
public enum QuestionType {

    MULTIPLE_CHOICE("multiple"),
    TRUE_OR_FALSE("boolean");

    private String type;

    QuestionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
