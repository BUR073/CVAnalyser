// SID: 2408078
package com.trackgenesis.auth;

import com.trackgenesis.util.Hashing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing the logic for a user to login
 * @author henryburbridge
 */
public class LoginAuth {
    private final String filePath;
    private final Hashing hash;
    private final Map<String, String> users;

    /**
     * Constructor
     * Calls method to load users from the file into HashMap
     * @param filePath file path for the file containing the user details
     */
    public LoginAuth(String filePath) {
        this.filePath = filePath;
        this.hash = new Hashing();
        this.users = new HashMap<>();
        loadUsersFromFile();
    }

    /**
     * Loads the users from the file into a HashMap
     */
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
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

    /**
     * Main logic to login
     * @param username the user's username
     * @param password the user's password
     * @return True if logged in, false if not
     */
    public boolean login(String username, String password) {
        String storedHashedPassword = this.users.get(username); // Retrieve the stored hashed password

        if (storedHashedPassword != null) {
            return this.hash.checkHash(password, storedHashedPassword); // Check the password
        } else {
            return false; // User not found
        }
    }
}