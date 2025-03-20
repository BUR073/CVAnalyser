// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileChooser;
import com.trackgenesis.util.FileReaderUtility;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.KeyboardReader;


import java.io.IOException;

/**
 * Job description class
 * @author henryburbridge
 */
public class JobDescription {

    private final KeyboardReader kbr;
    private final String uploadMenu;
    private final FileChooser fileChooser;
    private final RecordRepository recordRepo;


    /**
     * Constructor
     * @param kbr KeyboardReaderClass
     * @param getProperties GetProperties Class
     * @param recordRepo RecordRepository Class
     */
    public JobDescription(KeyboardReader kbr, GetProperties getProperties, RecordRepository recordRepo) {
        this.kbr = kbr;
        this.fileChooser = new FileChooser();
        this.recordRepo = recordRepo;
        this.uploadMenu = getProperties.get("upload.Menu","properties/menu.properties");
    }

    /**
     * Allows the user to choose how they wish to upload the job description
     * Calls method to make this happen
     */
    public void upload() {
        // init new FileReaderUtility class
        FileReaderUtility fileReaderUtility = new FileReaderUtility();
        // Get user choice
        int choice = this.kbr.getInt(this.uploadMenu);
        // Start switch statements, store all outputs of options in text
        String text = switch (choice) {
            case 1 -> this.kbr.getLongString("Enter the job description: "); // Get user input
            case 2 -> fileReaderUtility.getText(fileChooser.chooseFile()); // Choose a file and pass into getText method
            default -> "";
        };

        // Init new JobDescriptionNLP class and pass in text
        JobDescriptionNLP jobDescriptionNLP = new JobDescriptionNLP(text);
        try {
            // Save the job description in string format
            this.recordRepo.setJobDescriptionText(text);
            // Extract details using NLP and save the record
            this.recordRepo.saveRecord(jobDescriptionNLP.extractInformation());
        } catch (IOException e) {
            System.err.println("An error occurred during the job description extraction: " + e.getMessage());
        }

    }

}

