package com.trackgenesis.UI;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.User;

import java.io.IOException;

public class Menu {

    private final KeyboardReader kbr;
    private final JobDescription JD;
    private final User user;
    private final String loggedInMenuView;
    private final String loggedOutMenuView;

    public Menu() throws IOException {
        this.user = new User();
        this.kbr = new KeyboardReader();
        this.JD = new JobDescription();

        this.loggedInMenuView = """
                \n---------------------------------
                Welcome to Track Genesis!
                ---------------------------------
                1. 📁 Upload Job Description
                2. 📄 Show Job Description
                3. 📂 Upload CVs
                4. 📊 View Ranked CVs
                5. 🚪 Logout
                ---------------------------------
                Enter your choice""";

        this.loggedOutMenuView = """
                -------------------------
                Welcome to Track Genesis!
                -------------------------
                1. 🔑 Login
                2. 📝 Register
                -------------------------
                Enter your choice""";
    }

    public void showMenu() throws IOException {
        if (user.isLoggedIn()) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    }

    private void loggedInMenu() throws IOException {

        //System.out.println(loggedInMenuView);
        switch (this.kbr.getInt(loggedInMenuView + "\nEnter")) {
            case 1:
                JobDescriptionRecord jobData = JD.upload();

                System.out.println("\nData extracted\nReturning to main menu...\n");

                System.out.println("People: " + jobData.people());
                System.out.println("Locations: " + jobData.locations());
                System.out.println("Organizations: " + jobData.organizations());
                System.out.println("Dates: " + jobData.dates());
                System.out.println("Times: " + jobData.times());
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
//        System.out.println("Welcome to Track Genesis!");
//        System.out.println("1. Login");
//        System.out.println("2. Register");

        switch (this.kbr.getInt(this.loggedOutMenuView)) {
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
