// SID: 2408078
package com.trackgenesis.auth;
import com.trackgenesis.util.Hashing;

import java.io.*;



public class RegisterAuth {

    private final String filePath;
    private final Hashing hash;


    public RegisterAuth(String filePath) {
        this.filePath = filePath;
        this.hash = new Hashing();
    }

    public boolean register(String username, String password) {
        if (this.usernameDoesNotExist(username)) {
            File file = new File(filePath);
            boolean isEmpty = file.length() == 0; // Check if the file is empty

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true))) {
                if (!isEmpty) { // Add a newline only if the file is not empty
                    writer.newLine();
                }
                password = hash.hash(password);
                writer.write(username + "," + password);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean usernameDoesNotExist(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) { // Ensure there's at least a username
                    String existingUsername = parts[0].trim(); // Trim whitespace
                    if (existingUsername.equals(username)) {
                        return false; // Username already exists
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return true; // Username not found
    }

}