package com.trackgenesis;

import com.trackgenesis.UI.Login;
import com.trackgenesis.UI.Register;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class User {
    private String username;
    private boolean loggedIn;
    private final Register register;
    private final Login login;

    public User() throws IOException {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        properties.load(inputStream);


        String UserFilePath = properties.getProperty("user.file.path");

        this.loggedIn = false;
        this.register = new Register(UserFilePath);
        this.login = new Login(UserFilePath);
    }

    public void login() throws IOException {
        this.loggedIn = login.login();
        this.username = login.getUsername();
    }

    public void register() throws IOException {
        register.register();
    }

    public void logout(){
        this.loggedIn = false;
        System.out.println("Logged out\n\n");
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public String getUsername() {
        return this.username;
    }
}
