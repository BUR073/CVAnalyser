// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.menuActions.loggedIn.*;
import com.trackgenesis.menuActions.loggedOut.UserLoginAction;
import com.trackgenesis.menuActions.loggedOut.UserRegisterAction;
import com.trackgenesis.main.User;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Menu {

    private final KeyboardReader kbr;
    private final User user;
    private final Properties properties;

    private final String loggedInMenuView;
    private final String loggedOutMenuView;

    private final Map<Integer, UserAction<?>> loggedInActions;
    private final Map<Integer, UserAction<?>> loggedOutActions;


    private Map<Integer, UserAction<?>> loggedOutActionsMap(User user) {
        UserLoginAction userLoginAction = new UserLoginAction(this.user);
        UserRegisterAction userRegisterAction = new UserRegisterAction(this.user);
        Map<Integer, UserAction<?>> loggedOutActionCreate = new HashMap<>();
        loggedOutActionCreate.put(1, userLoginAction);
        loggedOutActionCreate.put(2, userRegisterAction);
        return loggedOutActionCreate;
    }

    private Map<Integer, UserAction<?>> loggedInActionsMap(User user, JobDescription JD, UploadCV uploadCV, ViewRankedCVs viewRankedCvs) {
        UserLogoutAction userLogoutAction = new UserLogoutAction(user);
        ShowJobDescriptionAction showJobDescriptionAction = new ShowJobDescriptionAction(JD);
        JobDescriptionUploadAction jobDescriptionUploadAction = new JobDescriptionUploadAction(JD);
        UploadCVAction uploadCVAction = new UploadCVAction(uploadCV);
        ViewRankedCVsAction viewRankedCVsAction = new ViewRankedCVsAction(viewRankedCvs);

        Map<Integer, UserAction<?>> loggedInActionCreate = new HashMap<>();
        loggedInActionCreate.put(1, jobDescriptionUploadAction);
        loggedInActionCreate.put(2, showJobDescriptionAction);
        loggedInActionCreate.put(3, uploadCVAction);
        loggedInActionCreate.put(4, viewRankedCVsAction);
        loggedInActionCreate.put(5, userLogoutAction);
        return loggedInActionCreate;
    }


    public Menu(KeyboardReader kbr) {
        UploadCV uploadCV = new UploadCV();
        ViewRankedCVs viewRankedCvs = new ViewRankedCVs();
        this.properties = new Properties();
        this.kbr = kbr;
        JobDescription JD = new JobDescription(this.kbr);
        this.user = new User(this.kbr);

        // Get the menu UI
        this.loggedInMenuView = this.getFromProperties("loggedInMenu");
        this.loggedOutMenuView = this.getFromProperties("loggedOutMenu");

        // Create and fill new hashmaps to store the action classes
        this.loggedInActions = loggedInActionsMap(this.user, JD, uploadCV, viewRankedCvs);
        this.loggedOutActions = loggedOutActionsMap(this.user);


    }

    private String getFromProperties(String name) {
        InputStream input = getClass().getClassLoader().getResourceAsStream("properties/menu.properties");
        try {
            this.properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }
        return this.properties.getProperty(name);
    }

    public void showMenu() {
        if (user.isLoggedIn()) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    }


    private void loggedInMenu() {
        int choice = this.kbr.getInt(loggedInMenuView);
        UserAction<?> action = this.loggedInActions.get(choice);

        if (action != null) {
            try {
                Object result = action.execute(); // Get the result of the action

                // Handle the result based on its type
                if (result instanceof JobDescriptionRecord record) {
                    // Store or process the record (e.g., add it to a list, display its contents)
                    System.out.println("Received JobDescriptionRecord: " + record);
                    // ... your logic to store or use the record ...
                } else if (result instanceof String message) {
                    // Handle a String result
                    System.out.println("Received message: " + message);
                }
                // ... add more 'else if' blocks for other return types ...

            } catch (IOException e) {
                System.err.println("An error occurred during the action: " + e.getMessage());

            } catch (ClassCastException e) {
                System.err.println("Unexpected return type from UserAction");

            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        this.showMenu();
    }

    private void loggedOutMenu() {
        int choice = this.kbr.getInt(loggedOutMenuView);
        UserAction<?> action = this.loggedOutActions.get(choice);

        if (action != null) {
            try {
                action.execute();
            } catch (IOException e) {
                System.err.println("An error occurred during the action: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
        this.showMenu();
    }

}