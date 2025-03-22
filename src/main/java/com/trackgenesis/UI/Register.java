// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.RegisterAuth;
import com.trackgenesis.main.User;
import com.trackgenesis.util.KeyboardReader;


import java.util.Objects;

/**
 * Register class
 * @author henryburbridge
 */
public class Register {

    private final RegisterAuth registerAuth;
    private final KeyboardReader kbr;
    private final User user;

    /**
     * Constructor
     *
     * @param kbr  KeyboardReader object
     * @param user User object
     */
    public Register(KeyboardReader kbr, User user) {
        this.registerAuth = new RegisterAuth();
        this.kbr = kbr;
        this.user = user;
    }

    /**
     * Allows user to register a new account
     * Stores account details in .txt file with passwords hashed
     */
    public void register() {
        // User enter username and password
        String username = kbr.getString("Enter username");
        String password = kbr.getString("Enter password");
        // Check that the username and password are different
        if (Objects.equals(username, password)) {
            System.out.println("Username and password cannot be the same");
        }
        // If registration is successful
        if(this.registerAuth.register(username, password)){
            System.out.println("User registered successfully");
            String choice = kbr.getString("\nWould you like to login to your account automatically? \nEnter 'Y' to do so or anything else to continue");
            // If the user wants to log in straight away
            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("YES")) {
                // Call login in function and pass username and password
                this.user.login(username, password);
            }

        } else {
            System.err.println("Username: " + username + " already exists. Registration unsuccessful.");

        }


    }
}
