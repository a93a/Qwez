package com.example.qwez.validator;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * Validate extension Class that for Regex Matching
 */
public class PatternValidate extends Validate {
    private final Pattern pattern;

    public PatternValidate(String errorMessage, @NonNull Pattern pattern) {
        super(errorMessage);
        this.pattern = pattern;
    }

    /**
     * Check if String is valid by matching to Regex Pattern
     * @param textToCheck to check
     * @return true if valid
     */
    public boolean isValid(String textToCheck) {
        return pattern.matcher(textToCheck).matches();
    }

}
