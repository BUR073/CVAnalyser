// SID: 2408078
package com.trackgenesis.auth;
import com.trackgenesis.util.Hashing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterAuth {

    private final String filePath;
    private final Hashing hash;

    public RegisterAuth(String filePath) {
        this.filePath = filePath;
        this.hash = new Hashing();
    }

    public void register(String username, String password) {
        File file = new File(filePath);
        boolean isEmpty = file.length() == 0; // Check if the file is empty

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!isEmpty) { // Add a newline only if the file is not empty
                writer.newLine();
            }
            password = hash.hash(password);
            writer.write(username + "," + password);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}