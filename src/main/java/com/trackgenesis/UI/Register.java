// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.RegisterAuth;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;
import java.util.Objects;

public class Register {

    private final RegisterAuth registerAuth;
    private final KeyboardReader kbr;

    public Register(String filePath, KeyboardReader kbr) {
        this.registerAuth = new RegisterAuth(filePath);
        this.kbr = kbr;
    }

    public void register() {
        String username = kbr.getString("Enter username");
        String password = kbr.getString("Enter password");
        if (Objects.equals(username, password)) {
            System.out.println("Username and password cannot be the same");
        }
        if(this.registerAuth.register(username, password)){
            System.out.println("User registered successfully");
        } else {
            System.out.println("Username: " + username + " already exists\nRegistration unsuccessful.");
        }


    }
}
