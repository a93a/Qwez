package com.example.qwez.bus.event;

/**
 * POJO class for {@link com.example.qwez.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class ChangePassowordEvent {

    private final String newPassword;

    public ChangePassowordEvent(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
