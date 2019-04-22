package com.example.qwez.validator;

import android.widget.EditText;

public abstract class Validate {
    protected String errorMessage;

    public Validate(String errorMessage) {
        errorMessage = errorMessage;
    }

    public abstract boolean isValid(EditText editText);

    public boolean hasErrorMessage() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
