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
    private final GetProperties getProperties;


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
        this.getProperties = getProperties;
        this.uploadMenu = this.getProperties.get("upload.Menu","properties/menu.properties");
    }

    /**
     * Allows the user to choose how they wish to upload the job description
     * Calls method to make this happen
     */
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
            this.recordRepo.setJobDescriptionText(text);
            this.recordRepo.saveRecord(jobDescriptionNLP.extractInformation());
        } catch (IOException e) {
            System.err.println("An error occurred during the job description extraction: " + e.getMessage());
        }

    }

}

