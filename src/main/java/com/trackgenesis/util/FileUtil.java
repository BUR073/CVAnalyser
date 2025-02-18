package com.trackgenesis.util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class FileUtil {

    public static String getUserFilePath() {
        String filePath;
        filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        return filePath;
    }

    public static String getJobDescriptionFilePath(){
        return "/Users/henryburbridge/CVAnalyser/src/main/resources/jobDescription.txt";
    };

    public static String chooseFilePath() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a File");

        File defaultDirectory = new File("/Users/henryburbridge/CVAnalyser/src/main/resources");  // Create a File object
        if (defaultDirectory.exists() && defaultDirectory.isDirectory()) { // Check!
            fileChooser.setCurrentDirectory(defaultDirectory);
        } else {
            System.out.println("Warning: Could not set default directory. Using current directory.");
            // Handle the case where the default directory doesn't exist or isn't a directory
            // Perhaps set to a different known safe location or just let it default.
        }
        
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file path: " + filePath);
            return filePath;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("File selection cancelled.");
            return null;

        } else {
            System.out.println("Error occurred during file selection.");
            return null;
        }

    }


}
