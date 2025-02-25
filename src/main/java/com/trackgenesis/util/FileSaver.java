package com.trackgenesis.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.InputStream;
import java.util.Properties;

public class FileSaver {
    private final FileExtractor convert;
    private final String startDir;
    private final String saveLocation;

    public FileSaver() throws IOException {
        this.convert = new FileExtractor();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        properties.load(inputStream);

        this.startDir = properties.getProperty("jchooser.start.dir");
        this.saveLocation = properties.getProperty("job.description.save.location");
    };

    public void saveUnknownFileType(String filePath, String saveLocation, String fileName) throws IOException {
        String fileType = convert.getFileType(filePath);
        System.out.println(fileType);


        switch(fileType){
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
    };
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

