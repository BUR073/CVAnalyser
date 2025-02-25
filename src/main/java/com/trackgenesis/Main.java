package com.trackgenesis;

import com.trackgenesis.UI.Menu;


import java.io.IOException;

public class Main {

    private final Menu menu;

    public Main(Menu menu) {
        this.menu = menu;
    }

    public void start() throws IOException {
        //this.menu.setLoggedIn(false);
        this.menu.showMenu();
    }

    public static void main(String[] args) {
        try {
            Menu menu = new Menu(); // Create the Menu object
            Main main = new Main(menu); // Pass the Menu object to Main
            main.start(); //start the menu.
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage()); // Handle the exception
            e.printStackTrace(); //print the stack trace for debugging.
        }
    }
}