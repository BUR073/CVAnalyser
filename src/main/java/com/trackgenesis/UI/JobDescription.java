// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.menuActions.jobDescription.SaveToNewFileAction;
import com.trackgenesis.menuActions.jobDescription.SaveUnknownFileTypeAction;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileSaver;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.KeyboardReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JobDescription {

    private final KeyboardReader kbr;
    private final String saveLocation;
    private final String fileName;
    //private final JobDescriptionNLP jobDescriptionNLP;
    private final String uploadMenu;
    private final FileSaver save;
    private final Map<Integer, UserAction<?>> uploadActions;
    private final RecordRepository recordRepo;
    private final GetProperties getProperties;


    public JobDescription(KeyboardReader kbr, GetProperties getProperties, RecordRepository recordRepo) {
        this.kbr = kbr;
        this.save = new FileSaver();
        this.recordRepo = recordRepo;
        this.getProperties = getProperties;
        this.saveLocation = this.getProperties.get("job.description.save.location","properties/file.properties");
        this.fileName = this.getProperties.get("job.description.file.name","properties/file.properties");
        this.uploadMenu = this.getProperties.get("upload.Menu","properties/menu.properties");


        this.uploadActions = new HashMap<>();
        populateUploadActionsMap();


    }

    private void populateUploadActionsMap(){
        this.uploadActions.put(1, new SaveToNewFileAction(this.save, this.kbr, this.saveLocation, this.fileName));
        this.uploadActions.put(2, new SaveUnknownFileTypeAction(this.save, this.fileName, this.saveLocation));
    }

    public void showJobDescription() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFullPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Job Description does not exist. Please upload one.");
        }
    }


    public void upload() {
        JobDescriptionNLP jobDescriptionNLP = new JobDescriptionNLP(this.getProperties);
        int choice = this.kbr.getInt(this.uploadMenu);
        UserAction<?> action = this.uploadActions.get(choice);

        if (action != null) {
            try {
                action.execute();
            } catch (IOException e) {
                System.err.println("An error occurred during the action: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
        try {
            this.recordRepo.saveRecord(jobDescriptionNLP.extractInformation());
        } catch (IOException e) {
            System.err.println("An error occurred during the job description extraction: " + e.getMessage());
        }

    }


    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

