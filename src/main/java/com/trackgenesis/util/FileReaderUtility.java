package com.trackgenesis.util;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class FileReaderUtility {

    public String getText(String filePath) {
        String fileExtension = getFileExtension(filePath).toLowerCase();
        StringBuilder content = new StringBuilder();

        try {
            content = switch (fileExtension) {
                case "txt" -> readTextFile(filePath);
                case "pdf" -> readPdfFile(filePath);
                case "docx" -> readDocxFile(filePath);
                case "doc" -> readDocFile(filePath);
                default -> throw new UnsupportedOperationException("File type not supported");
            };
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
            return "";
        }

        return content.toString();
    }

    private StringBuilder readTextFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content;
    }

    private StringBuilder readPdfFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            content.append(stripper.getText(document));
        }
        return content;
    }

    private StringBuilder readDocxFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream fis = new FileInputStream(filePath)) {
            XWPFDocument document = new XWPFDocument(fis);
            document.getParagraphs().forEach(paragraph -> content.append(paragraph.getText()).append(System.lineSeparator()));
        }
        return content;
    }

    private StringBuilder readDocFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream fis = new FileInputStream(filePath)) {
            HWPFDocument document = new HWPFDocument(fis);
            Range range = document.getRange();
            content.append(range.text());
        }
        return content;
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : filePath.substring(lastDotIndex + 1);
    }
}
