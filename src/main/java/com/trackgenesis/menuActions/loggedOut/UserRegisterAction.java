// SID: 2408078
package com.trackgenesis.menuActions.loggedOut;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.main.User;

/**
 * Class that implements the UserAction interface
 * @author henryburbridge
 */
public class UserRegisterAction implements UserAction<Void> {
    private final User user;

    /**
     * Constructor
     * @param user User object
     */
    public UserRegisterAction(User user) {
        this.user = user;

    }

    /**
     * Calls the register method in the User class
     * @return null
     */
    @Override
    public Void execute() {
        user.register();
        return null;
    }
}