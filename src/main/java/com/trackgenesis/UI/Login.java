// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.LoginAuth;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;

/**
 * Login class handles the UI for the user to login
 */
public class Login {

    private final LoginAuth loginAuth; // Make LoginService a member
    private final KeyboardReader kbr;
    private String username;


    /**
     * @param filePath - File Path of the username and password file
     */
    public Login(String filePath, KeyboardReader kbr) { // Constructor
        this.loginAuth = new LoginAuth(filePath);
        this.kbr = kbr;
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * @return boolean - Returns True when logged in, false when not
     */
    public boolean login() { // No need for throws here, handle in login
        boolean isLoggedIn = loginAuth.login(kbr.getString("Enter username"), kbr.getString("Enter password"));

        if (isLoggedIn) {
            this.username = username;
            return true;

        } else {
            System.out.println("Login failed.");
            return false;
        }
    }

    // Method overloading
    public boolean login(String username, String password) { // No need for throws here, handle in login
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


