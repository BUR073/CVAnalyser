package com.example;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Register {

    public static boolean usernameExists(String username, String filePath) {
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
        System.out.println("Attempting to write to file: " + filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode (true)
            String line = username + "," + password;
            System.out.println(line);
            writer.newLine(); // Add a new line
            writer.write(line);
            writer.flush();
            System.out.println("Registration Successful!.\n\n");
            Menu.loggedOutMenu();
        } catch (IOException e) {
            System.err.println("Error writing to user file: " + e.getMessage());

        }
    }



    public static void register() {
        String filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter username: ");
        String username = scanner.nextLine(); // Use nextLine() to handle spaces in usernames

        if (usernameExists(username, filePath)) {
            System.out.print("Username already exists, please choose another\n");
            register(); // Recursive loop
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine(); // Use nextLine() to handle spaces in passwords

        storeUserCredentials(username, password, filePath);
    }
}
