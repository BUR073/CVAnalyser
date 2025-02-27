package com.trackgenesis.UI;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.User;

import java.io.IOException;

public class Menu {

    private final KeyboardReader kbr;
    private final JobDescription JD;
    private final User user;


    public Menu() throws IOException {
        this.user = new User();
        this.kbr = new KeyboardReader();
        this.JD = new JobDescription();


    }

    public void showMenu() throws IOException {
        if (user.isLoggedIn()) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    }

    private void loggedInMenu() throws IOException {

        System.out.println("Welcome to Track Genesis " + user.getUsername() + "!");
        System.out.println("1. Upload Job Description");
        System.out.println("2. Show Job Description");
        System.out.println("3. Upload CVs");
        System.out.println("4. View Ranked CVs");
        System.out.println("5. Logout");
        int choice = this.kbr.getInt("Enter");
        switch (choice) {
            case 1:
                JD.start();
                break;

            case 2:
                JD.showJobDescription();
                break;

            case 3:
                break;

            case 4:
                break;

            case 5:
                user.logout();
                break;
        }

        showMenu();
    }

    private void loggedOutMenu() throws IOException {
        System.out.println("Welcome to Track Genesis!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int choice = this.kbr.getInt("Enter your choice");

        switch (choice) {
            case 1:
                user.login();
                break;

            case 2:
                user.register();
                break;
        }

        this.showMenu();

    }
}
