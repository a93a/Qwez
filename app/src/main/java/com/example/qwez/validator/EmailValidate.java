package com.example.qwez.validator;

import android.util.Patterns;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class EmailValidate extends PatternValidate {

    private static final String NOT_VALID_EMAIL = "Not a valid email address.";

    public EmailValidate() {
        super(NOT_VALID_EMAIL, Patterns.EMAIL_ADDRESS);
    }

}
