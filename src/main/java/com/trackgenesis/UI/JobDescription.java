// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.Interface.UserAction;
import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.actions.jobDescription.SaveToNewFileAction;
import com.trackgenesis.actions.jobDescription.SaveUnknownFileTypeAction;
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
    private final FileSaver save;
    private final String saveLocation;
    private final String fileName;
    private final JobDescriptionNLP jobDescriptionNLP;
    private final String uploadMenu;
    private final Map<Integer, UserAction<?>> uploadActions;


    public JobDescription(KeyboardReader kbr) throws IOException {
        this.kbr = kbr;
        this.save = new FileSaver();
        this.jobDescriptionNLP = new JobDescriptionNLP();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/file.properties");

        if (inputStream == null) {
            throw new IOException("Could not find /properties/file.properties");
        }

        properties.load(inputStream);

        this.saveLocation = properties.getProperty("job.description.save.location");
        this.fileName = properties.getProperty("job.description.file.name");

        // Load the next properties file: /properties/menu.properties
        InputStream menuInputStream = getClass().getResourceAsStream("/properties/menu.properties");

        if (menuInputStream == null) {
            throw new IOException("Could not find /properties/menu.properties");
        }

        properties.load(menuInputStream);

        // Get the uploadMenu property
        this.uploadMenu = properties.getProperty("uploadMenu");

        SaveToNewFileAction saveToNewFileAction = new SaveToNewFileAction(this.save, this.kbr, this.saveLocation, this.fileName);
        SaveUnknownFileTypeAction saveUnknownFileTypeAction = new SaveUnknownFileTypeAction(this.save, this.fileName, this.saveLocation);
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


    public JobDescriptionRecord upload() throws IOException {

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

        return this.jobDescriptionNLP.extractInformation();
    }


    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

