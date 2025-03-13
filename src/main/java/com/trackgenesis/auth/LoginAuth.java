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
    private final Map<String, String> users;


    public LoginAuth(String filePath) {
        this.filePath = filePath;
        this.hash = new Hashing();
        this.users = new HashMap<>();
    }


    private void loadUsersFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String savedUsername;
            String savedPassword;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    savedUsername = parts[0].trim();
                    savedPassword = parts[1].trim();
                    this.users.put(savedUsername, savedPassword);
                } else {
                    System.err.println("Invalid line in user file: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }

    }


    public boolean login(String username, String password) {
        loadUsersFromFile(this.filePath);
        String storedHashedPassword = this.users.get(username); // Retrieve the stored hashed password

        if (storedHashedPassword != null) {
            return this.hash.checkHash(password, storedHashedPassword); // Check the password
        } else {
            return false; // User not found
        }
    }
}