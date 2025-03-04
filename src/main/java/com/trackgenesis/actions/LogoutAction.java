package com.trackgenesis.actions;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.main.User;

import java.io.IOException;

public class LogoutAction implements UserAction<Void> {
    private final User user;
    private final Runnable logoutFunction;

    public LogoutAction(User user) {
        this.user = user;
        this.logoutFunction = user::logout; // Use a method reference
    }

    @Override
    public Void execute() throws IOException {
        logoutFunction.run(); // Call the logout() method
        return null;
    }
}