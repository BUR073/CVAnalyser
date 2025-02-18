package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for credentials
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Call the authenticateUser method from Login class
        if (Login.authenticateUser(username, password)) {
            System.out.println("Login successful! Welcome, " + username);
            Menu.showMenu();
        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();


    }
}
