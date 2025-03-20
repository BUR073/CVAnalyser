// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.records.RecordRepository;

/**
 * User action class that implements UserAction interface
 */
public class ShowJobDescriptionAction implements UserAction<Void> {

    private final RecordRepository recordRepo;

    /**
     * Constructor
     * @param recordRepo RecordRepository object
     */
    public ShowJobDescriptionAction(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }

    /**
     * Gets jobDescription text and prints it to terminal if it's not an empty string
     * @return null
     */
    @Override
    public Void execute() {
        String jobDescription = this.recordRepo.getJobDescriptionText();
        if (jobDescription != null) {
            System.out.println(jobDescription);
        } else {
            System.err.println("No records found");
        }


        return null;
    }
}