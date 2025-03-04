package com.trackgenesis.actions.loggedOut;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.main.User;

import java.io.IOException;

public class UserRegisterAction implements UserAction<Void> {
    private final User user;

    public UserRegisterAction(User user) {
        this.user = user;
    }

    @Override
    public Void execute() throws IOException {
        user.login(); // Call the login() method directly
        return null;
    }
}