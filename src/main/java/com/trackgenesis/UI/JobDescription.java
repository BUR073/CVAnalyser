package com.trackgenesis.UI;

import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.FileSaver;

import java.io.IOException;

public class JobDescription {

    private final KeyboardReader kbr;
    private final FileSaver save;
    private final String folderPath = "/Users/henryburbridge/CVAnalyser/src/main/resources/JobDescription";
    private final String fileName = "JobDescription";


    public JobDescription() {
        this.kbr = new KeyboardReader();
        this.save = new FileSaver();


    }

    public void start() throws IOException {
        System.out.println("How would you like to upload the job description?");
        System.out.println("1. Type");
        System.out.println("2. Upload File (DOC, DOCX or PDF (PDF preferred))");
        int choice = kbr.getInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                String jobDescription = kbr.getLongString("Enter the job description: ");

                save.saveToNewFile(jobDescription, this.folderPath, this.fileName);
            case 2:
                String filePath = save.chooseFile();
                save.saveUnknownFileType(filePath, this.folderPath, this.fileName);

        }

        System.out.println("\nJob Description upload complete\n\nReturning to main menu...\n");

    }

    public String getFullPath() {
        return folderPath + "/" + fileName + ".txt";
    }


}

