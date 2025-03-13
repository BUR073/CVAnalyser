// SID: 2408078
package com.trackgenesis.main;

import com.trackgenesis.UI.Login;
import com.trackgenesis.UI.Register;
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
        // Load file.properties
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/file.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Error loading properties file" + e.getMessage());
        }

        // Get the file path for the user file from the file.properties
        String UserFilePath = properties.getProperty("user.file.path");

        // Set var initial states
        this.loggedIn = false;
        this.register = new Register(UserFilePath, kbr, this);
        this.login = new Login(UserFilePath, kbr);
    }

    /**
     * Attempts to login the User
     * Calls login() from the login class
     * Calls get username from the login class and sets it
     *
     */
    public void login() {
        this.loggedIn = login.login();
        this.username = login.getUsername();
    }

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
