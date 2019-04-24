package com.example.qwez.validator;

import android.widget.EditText;

public abstract class Validate {
    protected String errorMessage;

    public Validate(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract boolean isValid(String textToCheck);

    public boolean hasErrorMessage() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
