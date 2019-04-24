package com.example.qwez.bus.event;

public class SignupEvent {

    private final String email;
    private final String nick;
    private final String password;

    public SignupEvent(String email, String nick, String password) {
        this.email = email;
        this.nick = nick;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
}
