package com.example.qwez.bus.event;

/**
 * POJO class for {@link com.example.qwez.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class BooleanEvent {

    private final boolean booleanValue;

    public BooleanEvent(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

}
