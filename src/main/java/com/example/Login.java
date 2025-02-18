package com.example;

public class Login {
    boolean isLoggedIn = false;

    public Login() {
        System.out.println("Login");
    }

    public static void main(String[] args) {
        new Login(); // Creating an object to trigger the constructor
    }
}
