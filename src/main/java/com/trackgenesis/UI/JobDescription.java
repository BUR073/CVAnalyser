// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.menuActions.jobDescription.SaveToNewFileAction;
import com.trackgenesis.menuActions.jobDescription.SaveUnknownFileTypeAction;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.FileSaver;
import com.trackgenesis.util.KeyboardReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JobDescription {

    private final KeyboardReader kbr;
    private final String saveLocation;
    private final String fileName;
    private final JobDescriptionNLP jobDescriptionNLP;
    private final String uploadMenu;
    private final Map<Integer, UserAction<?>> uploadActions;


    public JobDescription(KeyboardReader kbr){
        this.kbr = kbr;
        FileSaver save = new FileSaver();
        this.jobDescriptionNLP = new JobDescriptionNLP();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/file.properties");

        if (inputStream == null) {
            System.err.println("Could not find /properties/file.properties");
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Error in loading properties file");
        }

        this.saveLocation = properties.getProperty("job.description.save.location");
        this.fileName = properties.getProperty("job.description.file.name");

        // Load the next properties file: /properties/menu.properties
        InputStream menuInputStream = getClass().getResourceAsStream("/properties/menu.properties");

        if (menuInputStream == null) {
            System.err.println("Could not find /properties/menu.properties");
        }

        try {
            properties.load(menuInputStream);
        } catch (IOException e) {
            System.err.println("Error in loading properties file");
        }
        // Get the uploadMenu property
        this.uploadMenu = properties.getProperty("uploadMenu");

        SaveToNewFileAction saveToNewFileAction = new SaveToNewFileAction(save, this.kbr, this.saveLocation, this.fileName);
        SaveUnknownFileTypeAction saveUnknownFileTypeAction = new SaveUnknownFileTypeAction(save, this.fileName, this.saveLocation);
        this.uploadActions = new HashMap<>();
        this.uploadActions.put(1, saveToNewFileAction);
        this.uploadActions.put(2, saveUnknownFileTypeAction);


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


    public JobDescriptionRecord upload() {

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
            return jobDescriptionNLP.extractInformation();
        } catch (IOException e) {
            System.err.println("An error occurred during the job description extraction: " + e.getMessage());
        }
        return null;
    }


    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

