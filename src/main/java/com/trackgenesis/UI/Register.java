package com.trackgenesis.UI;

import com.trackgenesis.auth.RegisterService;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;

public class Register {

    private final RegisterService registerService;
    private KeyboardReader kbr;

    public Register(String filePath) {
        this.registerService = new RegisterService(filePath);
        this.kbr = new KeyboardReader();
    }

    public void register() throws IOException {
        String username = kbr.getString("Enter username");
        String password = kbr.getString("Enter password");
        this.registerService.register(username, password);


    };
}
