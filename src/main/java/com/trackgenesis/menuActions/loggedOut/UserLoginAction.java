// SID: 2408078
package com.trackgenesis.menuActions.loggedOut;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.main.User;


/**
 * Class that implements the UserAction interface
 * @author henryburbridge
 */
public class UserLoginAction implements UserAction<Void> {
    private final User user;

    /**
     * Constructor
     * @param user User object
     */
    public UserLoginAction(User user) {
        this.user = user;
    }

    /**
     * Calls login() method in the User class
     * @return null
     */
    @Override
    public Void execute() {
        user.login(); // Call the login() method directly
        return null;
    }
}