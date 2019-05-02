package com.example.qwez.repository;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    public static final String DEFAULT_MESSAGE = "No Network Connectivity";
    private final String message;

    public NoConnectivityException(String message) {
        this.message = message;
    }

    public NoConnectivityException(){
        this.message = DEFAULT_MESSAGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
