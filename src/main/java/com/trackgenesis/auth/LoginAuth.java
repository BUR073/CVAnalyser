// SID: 2408078
package com.trackgenesis.auth;

import com.trackgenesis.util.Hashing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LoginAuth {
    private final String filePath;
    private final Hashing hash;
    private Map<String, String> users;


    public LoginAuth(String filePath) throws IOException {
        this.filePath = filePath;
        this.hash = new Hashing();
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
        try {
            this.users = loadUsersFromFile(this.filePath);
        } catch (IOException e) {
            System.err.println("Failed to load users from file: " + e.getMessage());
        }
        String storedHashedPassword = this.users.get(username); // Retrieve the stored hashed password

        if (storedHashedPassword != null) {
            return this.hash.checkHash(password, storedHashedPassword); // Check the password
        } else {
            return false; // User not found
        }
    }
}