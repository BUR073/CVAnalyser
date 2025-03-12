// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.RegisterAuth;
import com.trackgenesis.main.User;
import com.trackgenesis.util.KeyboardReader;


import java.util.Objects;

public class Register {

    private final RegisterAuth registerAuth;
    private final KeyboardReader kbr;
    private final User user;

    public Register(String filePath, KeyboardReader kbr, User user) {
        this.registerAuth = new RegisterAuth(filePath);
        this.kbr = kbr;
        this.user = user;
    }

    public void register() {
        String username = kbr.getString("Enter username");
        String password = kbr.getString("Enter password");
        if (Objects.equals(username, password)) {
            System.out.println("Username and password cannot be the same");
        }
        if(this.registerAuth.register(username, password)){
            System.out.println("User registered successfully");
            String choice;
            choice = kbr.getString("\nWould you like to login to your account automatically? \nEnter Y to do so or press enter to continue");
            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("YES")) {
                this.user.login(username, password);
            }

        } else {
            System.out.println("Username: " + username + " already exists\nRegistration unsuccessful.");

        }


    }
}
