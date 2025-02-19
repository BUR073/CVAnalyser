package com.trackgenesis.auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private Map<String, String> users; // Store users in a Map

    public LoginService(String filePath) throws IOException {
        users = loadUsersFromFile(filePath);
    }

    private Map<String, String> loadUsersFromFile(String filePath) throws IOException {
        Map<String, String> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    userMap.put(username, password);
                } else {
                    System.err.println("Invalid line in user file: " + line);
                }
            }
        }
        return userMap;
    }

    public boolean login(String username, String password) {
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            System.out.println("Login successful!");
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }
}