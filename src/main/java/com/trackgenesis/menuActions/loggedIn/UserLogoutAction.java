// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.main.User;


/**
 * Class that implements UserAction interface
 * @author henryburbridge
 */
public class UserLogoutAction implements UserAction<Void> {
    private final User user;

    /**
     * Constructor
     * @param user User object
     */
    public UserLogoutAction(User user) {
        this.user = user;
    }

    /**
     * Calls logout() method within the User class
     * @return null
     */
    @Override
    public Void execute() {
        this.user.logout(); // Call the logout() method
        return null;
    }
}