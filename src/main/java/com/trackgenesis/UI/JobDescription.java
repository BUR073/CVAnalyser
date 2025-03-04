// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.FileSaver;
import com.trackgenesis.util.KeyboardReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JobDescription {

    private final KeyboardReader kbr;
    private final FileSaver save;
    private final String saveLocation;
    private final String fileName;
    private final JobDescriptionNLP jobDescriptionNLP;
    private final String uploadMenu;


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

        switch (kbr.getInt(this.uploadMenu, 1, 2)) {
            case 1:
                save.saveToNewFile(kbr.getLongString("Enter the job description: "),
                        this.saveLocation,
                        this.fileName);
                break;

            case 2:
                save.saveUnknownFileType(save.chooseFile(), this.saveLocation, this.fileName);
                break;

        }

        System.out.println("Job Description upload complete.\nExtracting Data...");

        return this.jobDescriptionNLP.extractInformation();
    }


    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

