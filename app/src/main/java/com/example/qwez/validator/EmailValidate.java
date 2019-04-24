package com.example.qwez.validator;

import android.util.Patterns;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class EmailValidate extends PatternValidate {

    private static final String NOT_VALID_EMAIL = "Not a valid email address.";
    private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public EmailValidate() {
        super(NOT_VALID_EMAIL,  Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE));
    }

}
