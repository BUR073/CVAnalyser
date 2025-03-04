// SID: 2408078
package com.trackgenesis.auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LoginAuth {
    private final Map<String, String> users; // Store users in a Map


    public LoginAuth(String filePath) throws IOException {
        users = loadUsersFromFile(filePath);
    }


    private Map<String, String> loadUsersFromFile(String filePath) throws IOException {
        Map<String, String> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userMap.put(parts[0].trim(), parts[1].trim());
                } else {
                    System.err.println("Invalid line in user file: " + line);
                }
            }
        }
        return userMap;
    }




    public boolean login(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}