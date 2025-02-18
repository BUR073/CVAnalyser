package com.trackgenesis.auth;


import com.trackgenesis.security.Encrypt;
import com.trackgenesis.ui.Menu;
import com.trackgenesis.util.SystemUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Login {
    public static boolean loggedIn = false; // Store whether a user is logged in
    public static String username; // Store the name of the user
    private static final String FILE_PATH = SystemUtil.getUserFilePath(); // Path to usernames/passwords

    // Now public so it can be used in other classes
    public static boolean authenticateUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");

                if (credentials.length == 2) {
                    String storedUsername = "error";
                    try {
                        storedUsername = Encrypt.decrypt(credentials[0].trim());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                    String storedPassword = "error";
                    try {
                        storedPassword = Encrypt.decrypt(credentials[1].trim());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        Login.username = storedUsername.trim();

                        return true;

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    public static void login() { // Corrected: removed extra class keyword
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter X to exit\n");
        // Ask for credentials
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (Objects.equals(username, "X")) {
            System.out.print("\n\n");
            Menu.showMenu();
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();


        // Call the authenticateUser method from Login class
        if (Login.authenticateUser(username, password)) {
            loggedIn = true;
            System.out.println("Login successful! \n\n");
            Menu.showMenu();
        } else {
            System.out.println("Invalid username or password.\n\n");
            login();
        }


    }

    // Getter function so that other classes can check whether a user is logged in
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    // Getter function so that other classes can know the name of the user
    public static String getUsername() {
        return username;
    }


}

