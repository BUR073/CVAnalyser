package com.trackgenesis.ui;

import com.trackgenesis.auth.Login;
import com.trackgenesis.auth.Register;
import com.trackgenesis.util.IntegerUtil;
import com.trackgenesis.util.SystemUtil;
import com.trackgenesis.core.JobDescription;

import java.util.Scanner;


public class Menu {

    public static void showMenu() {
        if (Login.loggedIn) {
            loggedInMenu();
        } else {
            loggedOutMenu();
        }
    }


    public static void loggedInMenu() {
        System.out.println("Welcome to the menu, " + Login.getUsername() + "!");
        System.out.println("1. Upload Job Description");
        System.out.println("2. Upload CVs");
        System.out.println("3. View Ranked CVs");
        System.out.println("4. Log out");
        System.out.println("Please enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = IntegerUtil.getIntegerInput(scanner);

        switch (choice) {
            case 1:
                JobDescription.upload();

            case 2:
                System.out.println("Upload CVs");

            case 3:
                System.out.println("View Ranked CVs");

            case 4:
                Login.logOut();
        }


    }

    public static void loggedOutMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CV Analyser");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please enter your choice: ");

        int choice = IntegerUtil.getIntegerInput(scanner);

        if (choice == 100) { // IntegerUtils.getIntegerInput returns 100 if input is invalid
            showMenu(); // Recursive loop
        }


        switch (choice) {
            case 1:
                Login.login();
                break;

            case 2:
                System.out.println("Register New User\n");
                Register.register();
                showMenu();
                break;

            case 3:
                SystemUtil.exit();

            default:
                System.out.println("Please enter a valid choice");
                showMenu(); // Recursive loop
                break;
        }


    }


}