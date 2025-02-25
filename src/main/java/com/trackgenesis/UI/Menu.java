package com.trackgenesis.UI;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;


public class Menu {

    private final KeyboardReader kbr;
    private boolean loggedIn;
    private final Register register;
    private final String filePath;
    private final JobDescription JD;

    public void setLoggedIn(boolean value) {
        this.loggedIn = value;
    }

    public Menu() throws IOException {
        this.kbr = new KeyboardReader();
        this.filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        this.register = new Register(filePath);
        this.JD = new JobDescription();


    };

    public void showMenu() throws IOException {
        if (loggedIn) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    };

    public void loggedInMenu() throws IOException {

        System.out.println("Welcome to Track Genesis");
        System.out.println("1. Upload Job Description");
        int choice = this.kbr.getInt("Enter");
        switch (choice) {
            case 1:
                JD.start();
                showMenu();
        }
    };

    public void loggedOutMenu() throws IOException {
        System.out.println("Welcome to Track Genesis!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int choice = this.kbr.getInt("Enter your choice: ");

        switch (choice) {
            case 1:
                Login login = new Login(this.filePath);
                this.setLoggedIn(login.login());
                break;

            case 2:
                register.register();
                break;
        }

        this.showMenu();

    };
}
