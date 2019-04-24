package com.example.qwez.bus.event;

public class BooleanEvent {

    private final boolean booleanValue;

    public BooleanEvent(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

}
