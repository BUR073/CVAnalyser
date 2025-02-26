package com.trackgenesis;
import com.trackgenesis.UI.Menu;

import java.io.IOException;

public class Main {

    private final Menu menu;

    public Main(Menu menu) {
        this.menu = menu;
    }

    public void start() throws IOException {
        this.menu.showMenu();
    }

    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            Main main = new Main(menu);
            main.start();
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage()); // Handle the exception

        }
    }
}