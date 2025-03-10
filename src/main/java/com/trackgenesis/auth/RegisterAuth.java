// SID: 2408078
package com.trackgenesis.auth;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterAuth {

    private final String filePath;

    public RegisterAuth(String filePath) {
        this.filePath = filePath;
    }

    public void register(String username, String password) throws IOException {
        File file = new File(filePath);
        boolean isEmpty = file.length() == 0; // Check if the file is empty

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!isEmpty) { // Add a newline only if the file is not empty
                writer.newLine();
            }
            writer.write(username + "," + password);
        } catch (IOException e) {
            throw new IOException("Error registering user: " + e.getMessage(), e);
        }
    }
}