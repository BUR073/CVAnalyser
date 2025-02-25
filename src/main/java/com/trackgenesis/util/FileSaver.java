package com.trackgenesis.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    public void saveToNewFile(String contents, String folderPath, String fileName) throws IOException {
        File folder = new File(folderPath);

        // Check if the folder exists, create it if not
        if (!folder.exists()) {
            if (!folder.mkdirs()) { // Use mkdirs() to create parent directories if needed
                throw new IOException("Failed to create folder: " + folderPath);
            }
        }

        File file = new File(folder, fileName + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contents);
        } catch (IOException e) {
            throw new IOException("Error saving file: " + file.getAbsolutePath(), e);
        }
    }


    }

