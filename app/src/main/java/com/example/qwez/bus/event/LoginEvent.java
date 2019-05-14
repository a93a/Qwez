package com.example.qwez.bus.event;

/**
 * POJO class for {@link com.example.qwez.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class LoginEvent {

    private final String email;
    private final String password;
    private final boolean rememberMe;

    public LoginEvent(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }
}
