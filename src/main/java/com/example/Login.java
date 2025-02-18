package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    public static boolean loggedIn = false;
    public static String username;
    private static final String FILE_PATH = "src/main/resources/users.txt";

    // Now public so it can be used in other classes
    public static boolean authenticateUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    String storedUsername = credentials[0].trim();
                    String storedPassword = credentials[1].trim();

                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        Login.username = storedUsername.trim();
                        loggedIn = true;
                        return true;

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getUsername() {
        return username;
    }


}

