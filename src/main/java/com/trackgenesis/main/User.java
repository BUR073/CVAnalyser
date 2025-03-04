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
 * @author Henry Burbridge
 */
public class User {
    private String username; // String to store username
    private boolean loggedIn; // Boolean for storing whether a user is logged in
    private final Register register; // Register class
    private final Login login;// Login class
    private final KeyboardReader kbr;

    /**
     * Constructor
     * Initiates properties, register and login
     * Sets initial state of loggedIn
     * @throws IOException if there is I/O error when loading the properties file
     */
    public User(KeyboardReader kbr) throws IOException {
        this.kbr = kbr;
        // Load file.properties
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/file.properties");
        properties.load(inputStream);

        // Get the file path for the user file from the file.properties
        String UserFilePath = properties.getProperty("user.file.path");

        // Set var initial states
        this.loggedIn = false;
        this.register = new Register(UserFilePath, this.kbr);
        this.login = new Login(UserFilePath, this.kbr);
    }

    /**
     * Attempts to login the User
     * Calls login() from the login class
     * Calls get username from the login class and sets it
     * @throws IOException if an I/O error occurs during the login process.
     */
    public void login() throws IOException {
        this.loggedIn = login.login();
        this.username = login.getUsername();
    }

    /**
     * Attempts to register a new user account
     * Calls the register() function from the register class
     * @throws IOException if an I/O error occurs during the registration process
     */
    public void register() throws IOException {
        register.register();
    }

    /**
     * Logs out the user by setting the boolean var loggedIn to false
     */
    public void logout(){
        this.loggedIn = false;
        System.out.println("Logged out\n\n");
    }

    /**
     * Getter function for isLoggedIn
     * @return loggedIn
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * Getter function for username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
}
