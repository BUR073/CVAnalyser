// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.menuActions.loggedIn.*;
import com.trackgenesis.menuActions.loggedOut.UserLoginAction;
import com.trackgenesis.menuActions.loggedOut.UserRegisterAction;
import com.trackgenesis.main.User;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.GetProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Menu {

    private final KeyboardReader kbr;
    private final User user;
    private final JobDescription JD;
    private final UploadCV uploadCV;
    private final ViewRankedCVs viewRankedCvs;
    private final RecordRepository recordRepo;

    private final String loggedInMenuView;
    private final String loggedOutMenuView;

    private final Map<Integer, UserAction<?>> loggedInActions;
    private final Map<Integer, UserAction<?>> loggedOutActions;



    public Menu() {
        GetProperties getProperties = new GetProperties();
        this.kbr = new KeyboardReader();
        this.uploadCV = new UploadCV();
        this.recordRepo = new RecordRepository();
        this.viewRankedCvs = new ViewRankedCVs(this.recordRepo);
        this.JD = new JobDescription(this.kbr, getProperties);
        this.user = new User(this.kbr);



        // Get the menu UI
        this.loggedInMenuView = getProperties.get("logged.In.Menu", "properties/menu.properties");
        this.loggedOutMenuView = getProperties.get("logged.Out.Menu", "properties/menu.properties");

        // Create and fill new hashmaps to store the action classes
        this.loggedInActions = new HashMap<>();
        this.loggedOutActions = new HashMap<>();
        this.populateLoggedInActionsMap();
        this.populateLoggedOutActionsMap();


    }

    private void populateLoggedOutActionsMap() {
        this.loggedOutActions.put(1, new UserLoginAction(this.user));
        this.loggedOutActions.put(2, new UserRegisterAction(this.user));
    }

    private void populateLoggedInActionsMap() {
        this.loggedInActions.put(1, new JobDescriptionUploadAction(this.JD));
        this.loggedInActions.put(2, new ShowJobDescriptionAction(this.JD));
        this.loggedInActions.put(3, new UploadCVAction(this.uploadCV));
        this.loggedInActions.put(4, new ViewRankedCVsAction(this.viewRankedCvs));
        this.loggedInActions.put(5, new UserLogoutAction(this.user));
    }


    public void showMenu() {
        if (user.isLoggedIn()) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    }


    private void loggedInMenu() {
        int choice = this.kbr.getInt(this.loggedInMenuView);
        UserAction<?> action = this.loggedInActions.get(choice);

        if (action != null) {
            try {
                Object result = action.execute(); // Get the result of the action

                // Handle the result based on its type
                if (result instanceof JobDescriptionRecord record) {
                    // Store or process the record (e.g., add it to a list, display its contents)
                    System.out.println("Received JobDescriptionRecord: " + record);
                    this.recordRepo.addJobDescriptionRecord(record);
                }

            } catch (IOException e) {
                System.err.println("An error occurred during the action: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        this.showMenu();
    }

    private void loggedOutMenu() {
        int choice = this.kbr.getInt(this.loggedOutMenuView);
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