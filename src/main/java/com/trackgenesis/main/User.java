// SID: 2408078
package com.trackgenesis.main;

import com.trackgenesis.UI.Login;
import com.trackgenesis.UI.Register;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to handle the User
 * Contains functions: login(), register(), logout(), isLoggedIn(), getUsername()
 *
 * @author Henry Burbridge
 */
public class User {
    private final Register register; // Register class
    private final Login login;// Login class
    private String username; // String to store username
    private boolean loggedIn; // Boolean for storing whether a user is logged in

    /**
     * Constructor
     * Initiates properties, register and login
     * Sets initial state of loggedIn
     *
     */
    public User(KeyboardReader kbr) {

        GetProperties getProperties = new GetProperties();
        // Load the user file path

        // Set var initial states
        this.loggedIn = false;
        this.register = new Register(kbr, this);
        this.login = new Login(kbr);
    }

    /**
     * Attempts to log in the User
     * Calls login() from the login class
     * Calls get username from the login class and sets it
     *
     */
    public void login() {
        // Call login method
        this.loggedIn = login.login();
        // Set the username
        this.username = login.getUsername();
    }

    /**
     * Same as other login() method but takes a username and password input
     * @param username User's username
     * @param password User's Password
     */
    public void login(String username, String password) {
        this.loggedIn = login.login(username, password);
        this.username = login.getUsername();
    }

    /**
     * Attempts to register a new user account
     * Calls the register() function from the register class
     */
    public void register() {
        register.register();
    }

    /**
     * Logs out the user by setting the boolean var loggedIn to false
     */
    public void logout() {
        this.loggedIn = false;
        System.out.println("Logged out\n\n");
    }

    /**
     * Getter function for isLoggedIn
     *
     * @return loggedIn
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

}
