// SID: 2408078
package com.trackgenesis.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * File extract class. This is used for taking PDF, doc and docx files and extracting the text to save to .txt files
 *
 * @author Henry Burbridge
 */
public class FileExtractor {


    public String getText(String filePath) {
        try (InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (fileInputStream != null) {
                return new String(fileInputStream.readAllBytes());
            } else {
                return ""; //return empty string if file not found.
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null; // Return null to indicate an error
        }
    }

    /**
     * To extract the contents of a docx file and save to a txt
     *
     * @param docxFilePath - the file path of the docx file
     * @param folderPath   - the folder to save the file too
     * @param newFileName  - what to name the file
     */
    public void docxToTxt(String docxFilePath, String folderPath, String newFileName) {
        if (docxFilePath == null || folderPath == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docxPath = Paths.get(docxFilePath);
        Path destinationPath = Paths.get(folderPath, newFileName + ".txt");
        Path folder = Paths.get(folderPath);

        if (!Files.exists(docxPath)) {
            System.err.println("DOCX file does not exist: " + docxPath);
        }

        if (!Files.isDirectory(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                System.err.println("Could not create folder: " + folderPath);
            }
        }

        try (XWPFDocument document = new XWPFDocument(Files.newInputStream(docxPath));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationPath.toFile()), StandardCharsets.UTF_8))) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String style = paragraph.getStyle();
                String text = paragraph.getText();

                if (text.trim().isEmpty()) {
                    writer.write("\n"); // Preserve blank lines
                    continue;
                }

                if (style != null && style.startsWith("Heading")) {
                    writer.write("\n" + text.trim() + "\n"); // Add extra lines for headings
                } else {
                    writer.write(text.trim() + "\n"); // Trim whitespace and add newline
                }
            }

        } catch (IOException e) {
            System.err.println("Error converting DOCX to TXT: " + e.getMessage());
        }
    }

    /**
     * To convert a doc to txt
     *
     * @param docFilePath          - file path of the doc file
     * @param destinationDirectory - where to save the file
     * @param newFileName          - the name of the file when saving
     */
    public void docToTxt(String docFilePath, String destinationDirectory, String newFileName) {
        if (docFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docPath = Paths.get(docFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");
        Path destinationFolder = Paths.get(destinationDirectory);

        if (!Files.exists(docPath)) {
            System.err.println("DOC file does not exist: " + docPath);
        }

        if (!Files.isDirectory(destinationFolder)) {
            try {
                Files.createDirectories(destinationFolder);
            } catch (IOException e) {
                System.err.println("Unable to create directory: " + destinationFolder);
            }
        }

        try (InputStream inputStream = Files.newInputStream(docPath);
             HWPFDocument document = new HWPFDocument(inputStream);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationPath.toFile()), StandardCharsets.UTF_8))) {

            WordExtractor extractor = new WordExtractor(document);
            String text = extractor.getText();
            writer.write(text);

        } catch (IOException e) {
            System.err.println("Error converting DOC to TXT: " + e.getMessage());
        }
    }

    /**
     * Convert PDF to a txt
     *
     * @param pdfFilePath          - path of the file to convert
     * @param destinationDirectory - path of were to save it
     * @param newFileName          - name of a file
     */
    public void pdfToTxt(String pdfFilePath, String destinationDirectory, String newFileName) {
        if (pdfFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path pdfPath = Paths.get(pdfFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");
        Path destinationFolder = Paths.get(destinationDirectory);

        if (!Files.exists(pdfPath)) {
            System.err.println("PDF file does not exist: " + pdfPath);
        }

        if (!Files.isDirectory(destinationFolder)) {
            try {
                Files.createDirectories(destinationFolder);
            } catch (IOException e) {
                System.err.println("Could not create directory: " + destinationFolder);
            }
        }

        try (PDDocument document = PDDocument.load(new java.io.File(pdfFilePath));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationPath.toFile()), StandardCharsets.UTF_8))) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            writer.write(text);

        } catch (IOException e) {
            System.err.println("Error converting PDF to TXT: " + e);
        }
    }

    /**
     * This function simply copies a .txt file into a new location
     *
     * @param sourceFilePath       - the path of the original .txt
     * @param destinationDirectory - where it is to be saved
     * @param newFileName          - the name it is to be saved as
     */
    public void copyAndRename(String sourceFilePath, String destinationDirectory, String newFileName) {
        if (sourceFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt"); // Append .txt
        Path destinationDir = Paths.get(destinationDirectory);


        if (!Files.exists(sourcePath)) {
            System.err.println("Source file does not exist: " + sourcePath);
        }

        if (!Files.isDirectory(destinationDir)) {
            try {
                Files.createDirectories(destinationDir);
            } catch (IOException e) {
                System.err.println("Unable to create directory: " + destinationDir);
            }
        }

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Unable to copy file: " + sourcePath);
        }
    }

    /**
     * Function to determine the MIME type of a file. (e.g., "image/jpeg", "text/plain")
     *
     * @param filePath - takes a filepath
     * @return The MIME type of the file (e.g., "image/jpeg", "text/plain"),
     * or null if the file path is invalid, the file does not exist, is a
     * directory, or if an IOException occurs during content type probing.
     * It is also possible for Files.probeContentType to return null if the
     * content type cannot be determined.
     */
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
            return null; // Or handle the error appropriately
        }
    }
}
