package com.trackgenesis.UI;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Menu {

    private final KeyboardReader kbr;
    private final JobDescription JD;
    private final User user;
    private final Properties properties;

    private final String loggedInMenuView;
    private final String loggedOutMenuView;


    public Menu() throws IOException {
        this.properties = new Properties();
        this.user = new User();
        this.kbr = new KeyboardReader();
        this.JD = new JobDescription();

        this.loggedInMenuView = this.getFromProperties("loggedInMenu");
        this.loggedOutMenuView = this.getFromProperties("loggedOutMenu");

    }

    private String getFromProperties(String name) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("properties/menu.properties");
        this.properties.load(input);
        return this.properties.getProperty(name);
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
        switch (this.kbr.getInt(loggedInMenuView)) {
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
