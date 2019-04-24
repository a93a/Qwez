package com.example.qwez.validator;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class PatternValidate extends Validate {
    private Pattern pattern;

    public PatternValidate(String errorMessage, @NonNull Pattern pattern) {
        super(errorMessage);
        this.pattern = pattern;
    }

    public boolean isValid(String textToCheck) {
        return pattern.matcher(textToCheck).matches();
    }

}
