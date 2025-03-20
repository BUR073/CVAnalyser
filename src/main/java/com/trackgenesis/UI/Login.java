// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.LoginAuth;
import com.trackgenesis.util.KeyboardReader;


/**
 * Login class handles the UI for the user to login
 * @author henryburbridge
 */
public class Login {

    private final LoginAuth loginAuth; // Make LoginService a member
    private final KeyboardReader kbr;
    private String username;


    /**
     * Constructor
     * @param filePath - File Path of the username and password file
     */
    public Login(String filePath, KeyboardReader kbr) { // Constructor
        this.loginAuth = new LoginAuth(filePath);
        this.kbr = kbr;
    }

    /**
     * Getter function for the username
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return boolean - Returns True when logged in, false when not
     */
    public boolean login() {
        boolean isLoggedIn = loginAuth.login(kbr.getString("Enter username"), kbr.getString("Enter password"));

        if (isLoggedIn) {
            return true;

        } else {
            System.err.println("Login failed.");
            return false;
        }
    }

    /**
     * Logic the same as the other login() function.
     * Uses method overloading to allow for the username and password to be passed into the method
     * @param username users' username
     * @param password users' password
     * @return Returns True when logged in, false when not
     */
    public boolean login(String username, String password) {
        boolean isLoggedIn = loginAuth.login(username, password);

        if (isLoggedIn) {
            this.username = username;
            return true;

        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}


