// SID: 2408078

package com.trackgenesis.main;

import com.trackgenesis.UI.Menu;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;


public class Main {
    /**
     * The main entry point for the application.
     * <p>
     * This method initializes the application, sets the console encoding to UTF-8,
     * creates a Menu object, and displays the main menu.
     *
     * @param args Command-line arguments (not used in this application).
     * @throws IOException If an I/O error occurs during menu display or encoding setup.
     */
    public static void main(String[] args) throws IOException {
        System.setProperty("console.encoding", "UTF-8");
        KeyboardReader kbr = new KeyboardReader();
        Menu menu = new Menu(kbr);
        menu.showMenu();

    }

}