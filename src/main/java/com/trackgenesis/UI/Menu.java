// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.actions.loggedIn.JobDescriptionUploadAction;
import com.trackgenesis.actions.loggedIn.ShowJobDescriptionAction;
import com.trackgenesis.actions.loggedIn.UploadCVAction;
import com.trackgenesis.actions.loggedIn.UserLogoutAction;
import com.trackgenesis.actions.loggedOut.UserLoginAction;
import com.trackgenesis.actions.loggedOut.UserRegisterAction;
import com.trackgenesis.main.User;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.UI.UploadCV;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Menu {

    private final KeyboardReader kbr;
    private final JobDescription JD;
    private final User user;
    private final Properties properties;

    private final String loggedInMenuView;
    private final String loggedOutMenuView;

    private final Map<Integer, UserAction<?>> loggedInActions;
    private final Map<Integer, UserAction<?>> loggedOutActions;


    public Menu(KeyboardReader kbr) throws IOException {
        UploadCV uploadCV = new UploadCV();
        this.properties = new Properties();
        this.kbr = kbr;
        this.JD = new JobDescription(this.kbr);
        this.user = new User(this.kbr);

        // Create instances of the action classes
        UserLogoutAction userLogoutAction = new UserLogoutAction(user);
        ShowJobDescriptionAction showJobDescriptionAction = new ShowJobDescriptionAction(this.JD);
        JobDescriptionUploadAction jobDescriptionUploadAction = new JobDescriptionUploadAction(this.JD);
        UploadCVAction uploadCVAction = new UploadCVAction(uploadCV);

        UserLoginAction userLoginAction = new UserLoginAction(this.user);
        UserRegisterAction userRegisterAction = new UserRegisterAction(this.user);


        // Get the menu UI
        this.loggedInMenuView = this.getFromProperties("loggedInMenu");
        this.loggedOutMenuView = this.getFromProperties("loggedOutMenu");

        // Create new hashmaps to store the action classes
        this.loggedInActions = new HashMap<>();
        this.loggedOutActions = new HashMap<>();

        // Add the action classes to the hashmaps
        this.loggedInActions.put(1, jobDescriptionUploadAction);
        this.loggedInActions.put(2, showJobDescriptionAction);
        this.loggedInActions.put(3, uploadCVAction);
        this.loggedInActions.put(5, userLogoutAction);

        this.loggedOutActions.put(1, userLoginAction);
        this.loggedOutActions.put(2, userRegisterAction);


    }

    private String getFromProperties(String name) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("properties/menu.properties");
        this.properties.load(input);
        return this.properties.getProperty(name);
    }


    public void showMenu() throws IOException {
       while (true) {
           if (user.isLoggedIn()) {
               this.loggedInMenu();
           } else {
               this.loggedOutMenu();
           }
       }
    }

    private void loggedInMenu() throws IOException {
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
    }

    private void loggedOutMenu() throws IOException {
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

    }

}
