package com.trackgenesis.UI;

import com.trackgenesis.auth.LoginService;

import java.io.IOException;
import java.util.Scanner; // Import Scanner

public class Login {

    private final LoginService loginService; // Make LoginService a member

    public Login(String filePath) throws IOException { // Constructor
        this.loginService = new LoginService(filePath);
    }

    public void login(Scanner scanner) { // No need for throws here, handle in login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean isLoggedIn = loginService.login(username, password);

        if (isLoggedIn) {
            System.out.println("Welcome, " + username + "!");
        } else {
            System.out.println("Login failed.");
        }
    }
}


