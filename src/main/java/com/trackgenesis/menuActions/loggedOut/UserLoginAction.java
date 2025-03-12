// SID: 2408078
package com.trackgenesis.menuActions.loggedOut;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.main.User;




public class UserLoginAction implements UserAction<Void> {
    private final User user;

    public UserLoginAction(User user) {
        this.user = user;
    }

    @Override
    public Void execute() {
        user.login(); // Call the login() method directly
        return null;
    }
}