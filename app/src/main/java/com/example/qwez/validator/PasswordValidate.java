package com.example.qwez.validator;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class PasswordValidate extends PatternValidate {

    private static final String PASSWORD_NOT_VALID = "Password must contain at least 1 lowercase character, 1 uppercase character, 1 special character, and be between 8 to 20 characters long.";
    private static final String PASSWORD_REGEX = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    public PasswordValidate() {
        super(PASSWORD_NOT_VALID, Pattern.compile(PASSWORD_REGEX));
    }

}
