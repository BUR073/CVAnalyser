package com.trackgenesis.UI;

import com.trackgenesis.auth.LoginService;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;

/**
 * Login class handles the UI for the user to login
 */
public class Login {

    private final LoginService loginService; // Make LoginService a member
    private KeyboardReader kbr;

    /**
     *
     * @param filePath - File Path of the username and password file
     */
    public Login(String filePath) throws IOException { // Constructor
        this.loginService = new LoginService(filePath);
        this.kbr = new KeyboardReader();
    }

    /**
     *
     * @return boolean - Returns True when logged in, false when not
     */
    public boolean login() { // No need for throws here, handle in login
        String username = kbr.getString("Enter username");

        String password = kbr.getString("Enter password");

        boolean isLoggedIn = loginService.login(username, password);

        if (isLoggedIn) {
            System.out.println("Welcome, " + username + "!");
            return true;

        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}


