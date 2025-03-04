// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.auth.RegisterAuth;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;

public class Register {

    private final RegisterAuth registerAuth;
    private final KeyboardReader kbr;

    public Register(String filePath, KeyboardReader kbr) throws IOException {
        this.registerAuth = new RegisterAuth(filePath);
        this.kbr = kbr;
    }

    public void register() throws IOException {
        String username = kbr.getString("Enter username");
        String password = kbr.getString("Enter password");
        this.registerAuth.register(username, password);


    }
}
