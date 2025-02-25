package com.trackgenesis;

import com.trackgenesis.UI.Login;
import com.trackgenesis.UI.Register;

import java.io.IOException;

public class User {
    private String username;
    private boolean loggedIn;
    private Register register;
    private String UserFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";

    public User() {
        this.UserFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        this.loggedIn = false;
    }

    public void login() throws IOException {
        Login login = new Login(this.UserFilePath);
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
