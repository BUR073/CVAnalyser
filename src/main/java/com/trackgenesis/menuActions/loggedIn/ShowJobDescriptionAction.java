// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.JobDescription;
import com.trackgenesis.records.RecordRepository;


public class ShowJobDescriptionAction implements UserAction<Void> {

    private final RecordRepository recordRepo;

    public ShowJobDescriptionAction(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }

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