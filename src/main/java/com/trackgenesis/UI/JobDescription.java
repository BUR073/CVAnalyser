package com.trackgenesis.UI;

import com.trackgenesis.NLP.JobDescriptionNLP;
import com.trackgenesis.records.JobDescriptionRecord;
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
    private final JobDescriptionNLP jobDescriptionNLP;


    public JobDescription() throws IOException {
        this.kbr = new KeyboardReader();
        this.save = new FileSaver();
        this.jobDescriptionNLP = new JobDescriptionNLP();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/application.properties");
        properties.load(inputStream);


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



    public JobDescriptionRecord upload() throws IOException {

        switch (uploadType()) {
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


    private int uploadType(){
        System.out.println("How would you like to upload the job description?");
        System.out.println("1. Type");
        System.out.println("2. Upload File (DOC, DOCX or PDF (PDF preferred))");
        return kbr.getInt("Enter your choice: ", 1, 2);
    }

    public String getFullPath() {
        return this.saveLocation + "/" + fileName + ".txt";
    }


}

