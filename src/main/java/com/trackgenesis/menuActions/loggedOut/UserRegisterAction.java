// SID: 2408078
package com.trackgenesis.menuActions.loggedOut;

import com.trackgenesis.menuActions.Interface.UserAction;
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