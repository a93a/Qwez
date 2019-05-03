package com.example.qwez.validator;

/**
 * Validate Class for checking if String is empty
 */
public class EmptyValidate extends Validate {

    private static final String EMPTY_ERROR_MESSAGE = "Field cannot be empty";

    public EmptyValidate() {
        super(EMPTY_ERROR_MESSAGE);
    }

    /**
     * Check if String is not empty
     * @param textToCheck to check
     * @return true if valid
     */
    @Override
    public boolean isValid(String textToCheck) {
        return textToCheck.length() > 0;
    }

}
