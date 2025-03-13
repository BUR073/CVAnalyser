// SID: 2408078

package com.trackgenesis.main;

import com.trackgenesis.UI.Menu;


public class Main {
    /**
     * The main entry point for the application.
     * This method initializes the application, sets the console encoding to UTF-8,
     * creates a Menu object, and displays the main menu.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        Menu menu = new Menu();
        menu.showMenu();

    }

}