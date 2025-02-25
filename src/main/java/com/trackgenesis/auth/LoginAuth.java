package com.trackgenesis.auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LoginServices handles the logic for the Login class
 */
public class LoginAuth {
    private final Map<String, String> users; // Store users in a Map

    /**
     *
     * @param filePath - File path of the username and password file
     * @throws IOException
     */
    public LoginAuth(String filePath) throws IOException {
        users = loadUsersFromFile(filePath);
    }

    /**
     *
     * @param filePath - File path of the username and password file
     * @return userMap - Return the username and password pair
     * @throws IOException
     */
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




    /**
     *
     * @param username
     * @param password
     * @return - True if logged in, false if not
     */
    public boolean login(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}