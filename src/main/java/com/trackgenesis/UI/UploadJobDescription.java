package com.trackgenesis.UI;

import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.FileSaver;


import java.io.IOException;

public class UploadJobDescription {

    private final KeyboardReader kbr;
    private final FileSaver save;


    public UploadJobDescription() {
        this.kbr = new KeyboardReader();
        this.save = new FileSaver();


    }

    public void start() throws IOException {
        String folderPath = "/Users/henryburbridge/CVAnalyser/src/main/resources/JobDescription";
        String newFileName = "JobDescription";

        System.out.println("How would you like to upload the job description?");
        System.out.println("1. Type");
        System.out.println("2. Upload File (DOC, DOCX or PDF (PDF preferred))");
        int choice = kbr.getInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                String jobDescription = kbr.getLongString("Enter the job description: ");

                save.saveToNewFile(jobDescription, folderPath, newFileName);
            case 2:
                String filePath = save.chooseFile();
                save.saveUnknownFileType(filePath, folderPath, newFileName);

        }
    }
}

