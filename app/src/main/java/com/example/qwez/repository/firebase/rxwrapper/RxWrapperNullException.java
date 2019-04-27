package com.example.qwez.repository.firebase.rxwrapper;

/**
 * RxWrapper specific Exception class
 */
public class RxWrapperNullException extends Exception {

    public final static String NO_CURRENT_USER = "No current user(s) found.";

    private final String message;

    public RxWrapperNullException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
