package com.trackgenesis.actions;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.UI.JobDescription;
import com.trackgenesis.records.JobDescriptionRecord; // Import the record
import java.io.IOException;

public class UploadAction implements UserAction<JobDescriptionRecord> { // Change class name to UploadAction

    private final JobDescription jobDescription; // Add a field to hold the JobDescription object

    public UploadAction(JobDescription jobDescription) { // Add a constructor to receive JobDescription
        this.jobDescription = jobDescription;
    }

    @Override
    public JobDescriptionRecord execute() throws IOException {
        return jobDescription.upload(); // Call the upload() method and return the result
    }
}