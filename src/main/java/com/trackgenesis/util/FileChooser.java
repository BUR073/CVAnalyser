// SID: 2408078
package com.trackgenesis.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to launch a GUI for choosing a file
 * @author henryburbridge
 */
public class FileChooser {

    private final String startDir;

    /**
     * Constructor
     * Defines a new GetProperties class
     * Defines the start directory for the GUI
     */
    public FileChooser(){
        // Init GetProperties class
        GetProperties properties = new GetProperties();
        // Load the start directory from the properties file
        this.startDir = properties.get("jchooser.start.dir", "properties/file.properties");
    }

    /**
     * Allows user to select multiple files
     * @param description a description of the file types to show
     * @param extensions a variable number of file extensions to filter by
     * @return a List of filePaths or null if there is an error
     */
    public List<String> chooseFiles(String description, String... extensions) {
        // Init new JFileChooser class and pass in start directory
        JFileChooser fileChooser = new JFileChooser(this.startDir);
        fileChooser.setMultiSelectionEnabled(true); // Enable multiple file selection
        // Check that method inputs are not empty
        if (description != null && extensions != null && extensions.length > 0) {
            // Set filters for the file chooser
            FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
            fileChooser.setFileFilter(filter);
        }
        // Open file chooser dialog
        int returnValue = fileChooser.showOpenDialog(null);

        // Check if the user has completed selection
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get selected files
            File[] selectedFiles = fileChooser.getSelectedFiles();
            // Init new array list
            List<String> filePaths = new ArrayList<>();
            // Loop through selected files and add paths to the array list
            for (File file : selectedFiles) {
                filePaths.add(file.getAbsolutePath());
            }
            return filePaths;
        } else {
            // Return null when the user terminates the GUI
            return null;
        }
    }
    /**
     * Allows user to select one file
     * @return - the file path of the chosen file or null if there is an error
     */
    public String chooseFile() {
        // Init new JFileChooser and set start dir
        JFileChooser fileChooser = new JFileChooser(this.startDir);

        // Set filters
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text and Document Files", "txt", "doc", "docx", "pdf");
        fileChooser.setFileFilter(filter);

        //open the file chooser dialog
        int returnValue = fileChooser.showOpenDialog(null);
        // Check if user has finished selecting file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Store selected file
            File selectedFile = fileChooser.getSelectedFile();
            // Return the absolute path of the selected file
            return selectedFile.getAbsolutePath();
        } else {
            // User canceled the file selection
            return null; 
        }
    }


}
