// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.menuActions.loggedIn.*;
import com.trackgenesis.menuActions.loggedOut.UserLoginAction;
import com.trackgenesis.menuActions.loggedOut.UserRegisterAction;
import com.trackgenesis.main.User;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.GetProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Menu class,
 * The main class in this program for the user, allows access to all the program functions
 * @author henryburbridge
 */

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


    /**
     * Constructor
     * Instantiates new objects of classes to be used around the program
     * Gets menu's from properties files
     * Calls functions to populate the action maps for the menu's
     */
    public Menu() {
        GetProperties getProperties = new GetProperties();
        this.kbr = new KeyboardReader();
        this.recordRepo = new RecordRepository();
        this.uploadCV = new UploadCV(this.recordRepo);
        this.viewRankedCvs = new ViewRankedCVs(this.recordRepo);
        this.JD = new JobDescription(this.kbr, getProperties, this.recordRepo);
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

    /**
     * Populate the logged-out menu action map
     */
    private void populateLoggedOutActionsMap() {
        this.loggedOutActions.put(1, new UserLoginAction(this.user));
        this.loggedOutActions.put(2, new UserRegisterAction(this.user));
    }

    /**
     * Populate the logged-in menu action map
     */
    private void populateLoggedInActionsMap() {
        this.loggedInActions.put(1, new JobDescriptionUploadAction(this.JD));
        this.loggedInActions.put(2, new ShowJobDescriptionAction(this.recordRepo));
        this.loggedInActions.put(3, new UploadCVAction(this.uploadCV));
        this.loggedInActions.put(4, new ViewRankedCVsAction(this.viewRankedCvs));
        this.loggedInActions.put(5, new UserLogoutAction(this.user));
    }


    /**
     * Show the correct menu for the user if they are logged in or logged out
     * The access method to this class
     */
    public void showMenu() {
        if (user.isLoggedIn()) {
            this.loggedInMenu();
        } else {
            this.loggedOutMenu();
        }
    }

    /**
     * Show the logged-in menu
     * Allow the user to select from the menu
     */
    private void loggedInMenu() {
        // Get user choice
        int choice = this.kbr.getInt(this.loggedInMenuView);
        // Get the UserAction object
        UserAction<?> action = this.loggedInActions.get(choice);
        // If there is an action
        if (action != null) {
            // Then execute
            action.execute();
        } else {
            // And if there isn't an action
            System.out.println("Invalid choice. Please try again.");
        }
        // Show the menu again
        this.showMenu();
    }

    /**
     * Show the logged-out menu
     * Allow the user to select from the menu
     */
    private void loggedOutMenu() {
        // Get user choice
        int choice = this.kbr.getInt(this.loggedOutMenuView);
        // Get the UserAction object
        UserAction<?> action = this.loggedOutActions.get(choice);
        // If there is an action
        if (action != null) {
            // Then execute
            action.execute();
        } else {
            // and if there is not
            System.out.println("Invalid choice. Please try again.");
        }
        // Show the menu again
        this.showMenu();
    }

}