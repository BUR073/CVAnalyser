package com.trackgenesis;

import com.trackgenesis.auth.Login;
import com.trackgenesis.auth.Register;
import com.trackgenesis.utils.IntegerUtils;
import com.trackgenesis.utils.SystemUtils;
import java.util.Scanner;


public class Menu {

    public static void showMenu() {
        if (Login.loggedIn) {
            loggedInMenu();
        } else {
            loggedOutMenu();
        }
    }



    public static void loggedInMenu(){
        System.out.println("Welcome to the menu, " + Login.getUsername() + "!");
        System.out.println("1. Upload Job Description");
        System.out.println("2. Upload CVs");
        System.out.println("3. View Ranked CVs");
        System.out.println("4. Log out");
        System.out.println("Please enter your choice: ");


    }

    public static void loggedOutMenu(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CV Analyser");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please enter your choice: ");

        int choice = IntegerUtils.getIntegerInput(scanner);

        if (choice == 100) { // IntegerUtils.getIntegerInput returns 100 if input is invalid
            loggedInMenu(); // Recursive loop
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
                SystemUtils.exit();

            default:
                System.out.println("Please enter a valid choice");
                loggedOutMenu(); // Recursive loop
                break;
        }



    }


}