package com.example.qwez.entity;

/**
 * POJO class for carrying error(s) in application. Used in Viewmodel LiveData error
 */
public class ErrorCarrier {

    private final String message;

    public ErrorCarrier(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
