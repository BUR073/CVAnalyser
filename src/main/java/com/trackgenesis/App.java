package com.trackgenesis;

import com.trackgenesis.security.Encryption;
import com.trackgenesis.ui.Menu;

public class App {
    public static void main(String[] args) {

        Encryption.createKey();        // Call createKey() on the instance

        Menu.showMenu();
    }
}