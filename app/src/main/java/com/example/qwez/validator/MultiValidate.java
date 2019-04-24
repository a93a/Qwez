package com.example.qwez.validator;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiValidate extends Validate {

    private final List<Validate> validators;

    public MultiValidate(String message,@NonNull Validate... validators) {
        super(message);
        this.validators = new ArrayList<>(Arrays.asList(validators));
    }

    public MultiValidate(String message) {
        super(message);
        this.validators = new ArrayList<>();
    }

    public void add(Validate validate) {
        validators.add(validate);
    }

    @Override
    public boolean isValid(String textToCheck) {
        for (Validate validate : validators) {
            if (!validate.isValid(textToCheck)) {
                this.errorMessage = validate.getErrorMessage();
                return false;
            }
        }
        return true;
    }
}
