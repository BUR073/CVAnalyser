// SID: 2408078
package com.trackgenesis.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class FileSaver
 * This class handles saving files
 *
 * @author Henry Burbridge
 */
public class FileSaver {
    private final FileExtractor convert;
    private final String startDir;

    /**
     * Constructor function
     * Creates new FileExtractor object
     * Loads properties file
     * Sets start directory for the file chooser
     *
     * @throws IOException
     */
    public FileSaver() throws IOException {
        this.convert = new FileExtractor();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/properties/file.properties");
        properties.load(inputStream);

        this.startDir = properties.getProperty("jchooser.start.dir");

    }

    /**
     * This function is used for saving a file when the type is unknown
     * Calls multiples functions including: copyAndRename, pdfToTxt, docToTxt and docxToTxt
     *
     * @param filePath     - the path of the file that you want to save
     * @param saveLocation - the path for where you want to save the file
     * @param fileName     - what you want to save the file name as
     * @throws IOException - if there is an I/O error
     */
    public void saveUnknownFileType(String filePath, String saveLocation, String fileName) throws IOException {

        switch (convert.getFileType(filePath)) {
            case "text/plain":
                // Convert and save .txt
                convert.copyAndRename(filePath, saveLocation, fileName);
                break;
            case "application/pdf":
                // Convert and save .pdf
                convert.pdfToTxt(filePath, saveLocation, fileName);
                break;
            case "application/msword":
                // Convert and save .doc
                convert.docToTxt(filePath, saveLocation, fileName);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                // Convert and save .docx
                convert.docxToTxt(filePath, saveLocation, fileName);
                break;

        }
        System.out.println("Successfully Saved");
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
     * @param contents   - String contain what you want to save to the file
     * @param folderPath - The folder path for where the file is to be saved
     * @param fileName   - The name for the file
     * @throws IOException - If there is an error saving the file
     */
    public void saveToNewFile(String contents, String folderPath, String fileName) throws IOException {
        File folder = new File(folderPath);

        File file = new File(folder, fileName + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contents);
        } catch (IOException e) {
            throw new IOException("Error saving file: " + file.getAbsolutePath(), e);
        }
    }


}

