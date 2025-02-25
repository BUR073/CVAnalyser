package com.trackgenesis.UI;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;


public class Menu {

    private final KeyboardReader kbr;
    private boolean loggedIn;
    private final Register register;
    private final String UserFilePath;
    private final JobDescription JD;
    private String JobDescriptionFilePath;

    public void setLoggedIn(boolean value) {
        this.loggedIn = value;
    }

    public Menu() throws IOException {
        this.kbr = new KeyboardReader();
        this.UserFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        this.register = new Register(UserFilePath);
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
        System.out.println("2. Upload CVs");
        System.out.println("3. View Ranked CVs");
        System.out.println("4. Logout");
        int choice = this.kbr.getInt("Enter");
        switch (choice) {
            case 1:
                JD.start();
                this.JobDescriptionFilePath = JD.getFullPath();
                showMenu();
            case 2:
                break;

            case 3:
                break;

            case 4:
                this.loggedIn = false;
                System.out.println("Logged out\n\n");
                showMenu();
                break;
        }
    };

    public void loggedOutMenu() throws IOException {
        System.out.println("Welcome to Track Genesis!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int choice = this.kbr.getInt("Enter your choice");

        switch (choice) {
            case 1:
                Login login = new Login(this.UserFilePath);
                this.setLoggedIn(login.login());
                break;

            case 2:
                register.register();
                break;
        }

        this.showMenu();

    };
}
