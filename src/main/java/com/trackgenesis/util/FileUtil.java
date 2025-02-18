package com.trackgenesis.util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileUtil {

    public static String getFileType(File file) {
        // text/plain - txt
        // application/pdf - pdf
        // application/msword - doc
        // application/vnd.openxmlformats-officedocument.wordprocessingml.document - docx
        try {
            Path path = Paths.get(file.getAbsolutePath());
            return Files.probeContentType(path); // Returns the MIME type (e.g., "text/plain", "image/jpeg") or null if it can't be determined.
        } catch (IOException e) {
            System.err.println("Error getting MIME type: " + e.getMessage());
            return null;
        }
    }


    public static String getUserFilePath() {
        String filePath;
        filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt";
        return filePath;
    }

    public static String getJobDescriptionFilePath(){
        return "/Users/henryburbridge/CVAnalyser/src/main/resources/jobDescription.txt";
    };

    public static File[] chooseFilePaths() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Files");
        fileChooser.setMultiSelectionEnabled(true);

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

            return fileChooser.getSelectedFiles();

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("File selection cancelled.");
            return null;

        } else {
            System.out.println("Error occurred during file selection.");
            return null;
        }

    }


}
