package com.trackgenesis.auth;

import com.trackgenesis.security.Encryption;
import com.trackgenesis.util.FileUtil;

import java.io.*;
import java.util.Scanner;

public class Register {

    public static boolean usernameExists(String username, String filePath) {
        try {
            username = Encryption.encrypt(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true; // Username found
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
            return false; // Assume username doesn't exist if there's an error
        }
        return false; // Username not found
    }

    public static void storeUserCredentials(String username, String password, String filePath) {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode (true)
            String line = username + "," + password;

            writer.newLine(); // Add a new line
            writer.write(line);
            writer.flush();
            System.out.println("Registration Successful!.\n\n");

        } catch (IOException e) {
            System.err.println("Error writing to user file: " + e.getMessage());

        }
    }


    public static void register() {
        String filePath = FileUtil.getUserFilePath();
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter username: ");
        String username = scanner.nextLine(); // Use nextLine() to handle spaces in usernames

        if (usernameExists(username, filePath)) {
            System.out.print("Username already exists, please choose another\n");
            register(); // Recursive loop
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine(); // Use nextLine() to handle spaces in passwords

        try {
            username = Encryption.encrypt(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            password = Encryption.encrypt(password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        storeUserCredentials(username, password, filePath);

    }
}