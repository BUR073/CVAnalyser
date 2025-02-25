package com.trackgenesis.UI;

import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.FileSaver;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class JobDescription {

    private final KeyboardReader kbr;
    private final FileSaver save;
    private final String folderPath = "/Users/henryburbridge/CVAnalyser/src/main/resources/JobDescription";
    private final String fileName = "JobDescription";


    public JobDescription() {
        this.kbr = new KeyboardReader();
        this.save = new FileSaver();


    }

    public void showJobDescription() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFullPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Job Description does not exist. Please upload one.");
        }
    }



    public void start() throws IOException {
        System.out.println("How would you like to upload the job description?");
        System.out.println("1. Type");
        System.out.println("2. Upload File (DOC, DOCX or PDF (PDF preferred))");
        int choice = kbr.getInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                String jobDescriptionString = kbr.getLongString("Enter the job description: ");
                save.saveToNewFile(jobDescriptionString, this.folderPath, this.fileName);
                break;

            case 2:
                String filePath = save.chooseFile();
                save.saveUnknownFileType(filePath, this.folderPath, this.fileName);
                break;

        }

        System.out.println("\nJob Description upload complete\n\nReturning to main menu...\n");

    }

    public String getFullPath() {
        return folderPath + "/" + fileName + ".txt";
    }


}

