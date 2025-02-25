package com.trackgenesis.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class FileConverter {

    public String getFileType(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null; // Or throw an IllegalArgumentException
        }

        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            return null; // Or handle non-existent/directory files appropriately
        }

        Path path = Paths.get(filePath);

        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            // Handle the exception (e.g., log it, return null, or throw a custom exception)
            e.printStackTrace(); // For debugging purposes
            return null; // Or handle the error appropriately
        }
    }
}
