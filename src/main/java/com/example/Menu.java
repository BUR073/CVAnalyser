package com.example;

public class Menu {

    public static void showMenu() {  // Method to display the menu
        if (Login.isLoggedIn()) { // Call isLoggedIn() as a method
            System.out.println("\n\nWelcome to the menu!");
            // ... rest of your menu options ...
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("3. Logout"); // Example logout option
            // ... handle user input for menu choices ...
        } else {
            System.out.println("You must be logged in to access the menu.");
        }
    }

    public static void main(String[] args) { // Example of how to call it
        showMenu();
    }
}