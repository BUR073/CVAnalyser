// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.JobDescription;
import com.trackgenesis.records.JobDescriptionRecord;


/**
 * User action class that implements UserAction
 * @author henryburbridge
 */
public class JobDescriptionUploadAction implements UserAction<JobDescriptionRecord> { // Change class name to UploadAction

    private final JobDescription jobDescription; // Add a field to hold the JobDescription object

    /**
     * Constructor
     * @param jobDescription JobDescription object
     */
    public JobDescriptionUploadAction(JobDescription jobDescription) { // Add a constructor to receive JobDescription
        this.jobDescription = jobDescription;
    }

    /**
     * Calls upload() method withing JobDescription class
     * @return null
     */
    @Override
    public Void execute() {
        jobDescription.upload(); // Call the upload() method and return the result
        return null;
    }
}