package com.trackgenesis.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileChooser {

    private final String startDir;

    public FileChooser(){
        GetProperties properties = new GetProperties();
        this.startDir = properties.get("jchooser.start.dir", "properties/file.properties");
    }

    public List<String> chooseFiles(String description, String... extensions) {
        JFileChooser fileChooser = new JFileChooser(this.startDir);
        fileChooser.setMultiSelectionEnabled(true); // Enable multiple file selection

        if (description != null && extensions != null && extensions.length > 0) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
            fileChooser.setFileFilter(filter);
        }

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            List<String> filePaths = new ArrayList<>();
            for (File file : selectedFiles) {
                filePaths.add(file.getAbsolutePath());
            }
            return filePaths;
        } else {
            return null; // or return an empty list: return new ArrayList<>();
        }
    }
    /**
     * Function to be able to choose the file which you wish to save
     *
     * @return - the file path of the chosen file or null if there is an error
     */
    public String chooseFile() {
        JFileChooser fileChooser = new JFileChooser(this.startDir);


        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text and Document Files", "txt", "doc", "docx", "pdf");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null); //opens the file chooser dialog

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath(); //returns the absolute path of the selected file
        } else {
            // User canceled the file selection
            return null; // or handle cancellation appropriately
        }
    }

    /**
     * Function used to save a string to a .txt file.
     * This is used when the user wants to type instead of uploading a file
     *
     * @param contents   - String contains what you want to save to the file
     * @param folderPath - The folder path for where the file is to be saved
     * @param fileName   - The name for the file
     */

}
