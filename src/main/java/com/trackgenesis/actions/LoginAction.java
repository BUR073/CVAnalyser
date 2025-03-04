package com.trackgenesis.actions;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.main.User;

import java.io.IOException;

public class LoginAction implements UserAction<Void> {
    private final User user;

    public LoginAction(User user) {
        this.user = user;
    }

    @Override
    public Void execute() throws IOException {
        user.login(); // Call the login() method directly
        return null;
    }
}