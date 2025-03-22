// SID: 2408078
package com.trackgenesis.auth;
import com.trackgenesis.util.Hashing;

import java.io.*;


/**
 * Class containing the logic for registering a new user
 * @author henryburbridge
 */
public class RegisterAuth {

    private final String filePath;
    private final Hashing hash;


    /**
     * Constructor
     * Defines a new Hashing object
     * @param filePath Filepath for user data storage file
     */
    public RegisterAuth(String filePath) {
        this.filePath = filePath;
        this.hash = new Hashing();
    }

    /**
     * Main logic for registering a user
     * @param username new username
     * @param password new password
     * @return true if successfully register, false otherwise
     */
    public boolean register(String username, String password) {
        // Check the username does not already exist
        if (!this.usernameExist(username)) {
            // Load the user file
            File file = new File(filePath);
            // Check if the file is empty
            boolean isEmpty = file.length() == 0;
            // Load BufferedWrite object in try statement so that it closes automatically
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true))) {
                if (!isEmpty) { // Add a newline only if the file is not empty
                    writer.newLine();
                }
                // Hash the password
                password = hash.hash(password);
                // Write the username and password to the file
                writer.write(username + "," + password);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            // return true as registration successful
            return true;
        } else {
            // return false as the registration was unsuccessful
            return false;
        }
    }

    /**
     * Checks whether the username already exists
     * @param username new username
     * @return true if the username already exists, false if not
     */
    private boolean usernameExist(String username) {
        // Load BufferedWrite object in try statement so that it closes automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            // While the username is not found
            while ((line = reader.readLine()) != null) {
                // Split the line into two parts
                String[] parts = line.split(",");
                // Split of the username and trim whitespace
                String existingUsername = parts[0].trim();
                if (existingUsername.equals(username)) {
                    return true; // Username already exists
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return false; // Username not found
    }

}