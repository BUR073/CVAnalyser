// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.UI.JobDescription;
import com.trackgenesis.records.JobDescriptionRecord;

import java.io.IOException;

public class JobDescriptionUploadAction implements UserAction<JobDescriptionRecord> { // Change class name to UploadAction

    private final JobDescription jobDescription; // Add a field to hold the JobDescription object

    public JobDescriptionUploadAction(JobDescription jobDescription) { // Add a constructor to receive JobDescription
        this.jobDescription = jobDescription;
    }

    @Override
    public JobDescriptionRecord execute() throws IOException {
        return jobDescription.upload(); // Call the upload() method and return the result
    }
}