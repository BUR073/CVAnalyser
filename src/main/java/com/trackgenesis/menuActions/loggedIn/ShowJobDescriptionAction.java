// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.UI.JobDescription;

import java.io.IOException;

public class ShowJobDescriptionAction implements UserAction<Void> {

    private final JobDescription jobDescription;

    public ShowJobDescriptionAction(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public Void execute() throws IOException {
        jobDescription.showJobDescription(); // Call showJobDescription() here

        return null;
    }
}