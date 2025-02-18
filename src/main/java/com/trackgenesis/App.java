package com.trackgenesis;

import com.trackgenesis.security.Encrypt;

public class App {
    public static void main(String[] args) {

        Encrypt.createKey();        // Call createKey() on the instance

        Menu.showMenu();
    }
}