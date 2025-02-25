package com.trackgenesis.UI;

import com.trackgenesis.util.KeyboardReader;
import com.trackgenesis.util.FileSaver;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class JobDescription {

    private final KeyboardReader kbr;
    private final FileSaver save;
    private final String saveLocation;
    private final String fileName;


    public JobDescription() throws IOException {
        this.kbr = new KeyboardReader();
        this.save = new FileSaver();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        properties.load(inputStream);

        //this.startDir = properties.getProperty("jchooser.start.dir");
        this.saveLocation = properties.getProperty("job.description.save.location");
        this.fileName = properties.getProperty("job.description.file.name");


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



    public void start() throws IOException {
        System.out.println("How would you like to upload the job description?");
        System.out.println("1. Type");
        System.out.println("2. Upload File (DOC, DOCX or PDF (PDF preferred))");
        int choice = kbr.getInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                String jobDescriptionString = kbr.getLongString("Enter the job description: ");
                save.saveToNewFile(jobDescriptionString, this.saveLocation, this.fileName);
                break;

            case 2:
                String filePath = save.chooseFile();
                save.saveUnknownFileType(filePath, this.saveLocation, this.fileName);
                break;

        }

        System.out.println("\nJob Description upload complete\n\nReturning to main menu...\n");

    }

    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

