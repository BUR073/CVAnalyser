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
        GetProperties properties = new GetProperties();
        this.startDir = properties.get("jchooser.start.dir", "properties/file.properties");
    }

    /**
     *
     * @param description a description of the file types to show
     * @param extensions a variable number of file extensions to filter by
     * @return a List of filePaths or null if there is an error
     */
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


}
