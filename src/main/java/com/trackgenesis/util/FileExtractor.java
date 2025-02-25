package com.trackgenesis.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class FileExtractor {

    public static void docxToTxt(String docxFilePath, String folderPath, String newFileName) throws IOException {
        if (docxFilePath == null || folderPath == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docxPath = Paths.get(docxFilePath);
        Path destinationPath = Paths.get(folderPath, newFileName + ".txt");

        if (!Files.exists(docxPath)) {
            throw new IOException("DOCX file does not exist: " + docxPath);
        }

        if (!Files.isDirectory(Paths.get(folderPath))) {
            Files.createDirectories(Paths.get(folderPath));
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


    public void docToTxt(String docFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (docFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path docPath = Paths.get(docFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");

        if (!Files.exists(docPath)) {
            throw new IOException("DOC file does not exist: " + docPath);
        }

        if (!Files.isDirectory(Paths.get(destinationDirectory))) {
            Files.createDirectories(Paths.get(destinationDirectory));
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

    public void pdfToTxt(String pdfFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (pdfFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path pdfPath = Paths.get(pdfFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt");

        if (!Files.exists(pdfPath)) {
            throw new IOException("PDF file does not exist: " + pdfPath);
        }

        if (!Files.isDirectory(Paths.get(destinationDirectory))) {
            Files.createDirectories(Paths.get(destinationDirectory));
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

    public void copyAndRename(String sourceFilePath, String destinationDirectory, String newFileName) throws IOException {
        if (sourceFilePath == null || destinationDirectory == null || newFileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationDirectory, newFileName + ".txt"); // Append .txt

        if (!Files.exists(sourcePath)) {
            throw new IOException("Source file does not exist: " + sourcePath);
        }

        if (!Files.isDirectory(Paths.get(destinationDirectory))) {
            Files.createDirectories(Paths.get(destinationDirectory));
        }

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error copying and renaming file: ", e);
        }
    }

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
