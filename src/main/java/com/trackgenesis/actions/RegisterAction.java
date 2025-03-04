package com.trackgenesis.actions;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.main.User;

import java.io.IOException;

public class RegisterAction implements UserAction<Void> {
    private final User user;

    public RegisterAction(User user) {
        this.user = user;
    }

    @Override
    public Void execute() throws IOException {
        user.login(); // Call the login() method directly
        return null;
    }
}