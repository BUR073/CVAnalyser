// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileChooser;
import com.trackgenesis.util.FileReaderUtility;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.KeyboardReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class JobDescription {

    private final KeyboardReader kbr;
    private final String saveLocation;
    private final String fileName;
    //private final JobDescriptionNLP jobDescriptionNLP;
    private final String uploadMenu;
    private final FileChooser fileChooser;
    private final RecordRepository recordRepo;
    private final GetProperties getProperties;


    public JobDescription(KeyboardReader kbr, GetProperties getProperties, RecordRepository recordRepo) {
        this.kbr = kbr;
        this.fileChooser = new FileChooser();
        this.recordRepo = recordRepo;
        this.getProperties = getProperties;
        this.saveLocation = this.getProperties.get("job.description.save.location","properties/file.properties");
        this.fileName = this.getProperties.get("job.description.file.name","properties/file.properties");
        this.uploadMenu = this.getProperties.get("upload.Menu","properties/menu.properties");
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
        FileReaderUtility fileReaderUtility = new FileReaderUtility();
        int choice = this.kbr.getInt(this.uploadMenu);
        String text = switch (choice) {
            case 1 -> this.kbr.getLongString("Enter the job description: ");
            case 2 -> fileReaderUtility.getText(fileChooser.chooseFile());
            default -> "";
        };

        JobDescriptionNLP jobDescriptionNLP = new JobDescriptionNLP(this.getProperties, text);
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

