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
 * File extract class. This is used for taking pdf, doc and docx files and extracting the text to save to .txt files
 *
 * @author Henry Burbridge
 */
public class FileExtractor {

    /**
     * To extract the contents of a docx file and save to a txt
     *
     * @param docxFilePath - the file path of the docx file
     * @param folderPath   - the folder to save the file too
     * @param newFileName  - what to name the file
     * @throws IOException - if there is an error extracting data from the file
     */
    public void docxToTxt(String docxFilePath, String folderPath, String newFileName) throws IOException {
        if (docxFilePath == null || folderPath == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docxPath = Paths.get(docxFilePath);
        Path destinationPath = Paths.get(folderPath, newFileName + ".txt");
        Path folder = Paths.get(folderPath);

        if (!Files.exists(docxPath)) {
            throw new IOException("DOCX file does not exist: " + docxPath);
        }

        if (!Files.isDirectory(folder)) {
            Files.createDirectories(folder);
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
            throw new IOException("Error converting DOCX to TXT: " + e.getMessage(), e);
        }
    }

    /**
     * To convert a doc to txt
     *
     * @param docFilePath          - file path of the doc file
     * @param destinationDirectory - where to save the file
     * @param newFileName          - the name of the file when saving
     * @throws IOException - if there is an error extracting data from the file
     */
    public void docToTxt(String docFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (docFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docPath = Paths.get(docFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");
        Path destinationFolder = Paths.get(destinationDirectory);

        if (!Files.exists(docPath)) {
            throw new IOException("DOC file does not exist: " + docPath);
        }

        if (!Files.isDirectory(destinationFolder)) {
            Files.createDirectories(destinationFolder);
        }

        try (InputStream inputStream = Files.newInputStream(docPath);
             HWPFDocument document = new HWPFDocument(inputStream);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationPath.toFile()), StandardCharsets.UTF_8))) {

            WordExtractor extractor = new WordExtractor(document);
            String text = extractor.getText();
            writer.write(text);

        } catch (IOException e) {
            throw new IOException("Error converting DOC to TXT: ", e);
        }
    }

    /**
     * Convert pdf to a txt
     *
     * @param pdfFilePath          - path of the file to convert
     * @param destinationDirectory - path of were to save it
     * @param newFileName          - name of file
     * @throws IOException - if there is an error converting or saving file
     */
    public void pdfToTxt(String pdfFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (pdfFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path pdfPath = Paths.get(pdfFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");
        Path destinationFolder = Paths.get(destinationDirectory);

        if (!Files.exists(pdfPath)) {
            throw new IOException("PDF file does not exist: " + pdfPath);
        }

        if (!Files.isDirectory(destinationFolder)) {
            Files.createDirectories(destinationFolder);
        }

        try (PDDocument document = PDDocument.load(new java.io.File(pdfFilePath));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationPath.toFile()), StandardCharsets.UTF_8))) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            writer.write(text);

        } catch (IOException e) {
            throw new IOException("Error converting PDF to TXT: ", e);
        }
    }

    /**
     * This function simply copies a .txt file into a new location
     *
     * @param sourceFilePath       - the path of the original .txt
     * @param destinationDirectory - where it is to be saved
     * @param newFileName          - the name it is to be saved as
     * @throws IOException - if there is an error saving
     */
    public void copyAndRename(String sourceFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (sourceFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt"); // Append .txt
        Path destinationDir = Paths.get(destinationDirectory);

        if (!Files.exists(sourcePath)) {
            throw new IOException("Source file does not exist: " + sourcePath);
        }

        if (!Files.isDirectory(destinationDir)) {
            Files.createDirectories(destinationDir);
        }

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error copying and renaming file: ", e);
        }
    }

    /**
     * Function to determine the MIME type of a file. (e.g., "image/jpeg", "text/plain")
     *
     * @param filePath - takes a filepath
     * @return -The MIME type of the file (e.g., "image/jpeg", "text/plain"),
     * or null if the file path is invalid, the file does not exist, is a
     * directory, or if an IOException occurs during content type probing.
     * It is also possible for Files.probeContentType to return null, if the
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
