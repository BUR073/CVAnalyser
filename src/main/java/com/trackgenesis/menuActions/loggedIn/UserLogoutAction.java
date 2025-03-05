// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.main.User;

import java.io.IOException;

public class UserLogoutAction implements UserAction<Void> {
    private final Runnable logoutFunction;

    public UserLogoutAction(User user) {
        this.logoutFunction = user::logout; // Use a method reference
    }

    @Override
    public Void execute() throws IOException {
        logoutFunction.run(); // Call the logout() method
        return null;
    }
}