package com.example.qwez.validator;

/**
 * Abstract Class for Validation
 */
public abstract class Validate {
    protected String errorMessage;

    public Validate(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Checks if {@code textToCheck} is valid
     * @param textToCheck to check
     * @return true if valid
     */
    public abstract boolean isValid(String textToCheck);

    /**
     * @return true is Validate has error message
     */
    public boolean hasErrorMessage() {
        return errorMessage != null;
    }

    /**
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
