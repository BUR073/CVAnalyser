package com.trackgenesis.actions.loggedIn;

import com.trackgenesis.Interface.UserAction;
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