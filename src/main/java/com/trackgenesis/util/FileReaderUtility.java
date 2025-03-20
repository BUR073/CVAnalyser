// SID: 2408078
package com.trackgenesis.util;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;

/**
 * Class for reading files
 * @author henryburbridge
 */
public class FileReaderUtility {

    /**
     *
     * @param filePath the path to the file to get text from
     * @return the string version of the file
     */
    public String getText(String filePath) {
        String fileExtension = getFileExtension(filePath).toLowerCase();
        StringBuilder content;


        content = switch (fileExtension) {
            case "txt" -> readTextFile(filePath);
            case "pdf" -> readPdfFile(filePath);
            case "docx" -> readDocxFile(filePath);
            case "doc" -> readDocFile(filePath);
            default -> throw new IllegalStateException("Unexpected value: " + fileExtension);

        };

        return content.toString();
    }

    /**
     * Get text from a .txt file
     * @param filePath the path to the file to get text from
     * @return the string of the contents
     */
    private StringBuilder readTextFile(String filePath) {
        // Init string builder to store string
        StringBuilder content = new StringBuilder();
        // Try statement to make sure BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read lines from the file until the end of the file is reached
            while ((line = reader.readLine()) != null) {
                // Append the lines to the StringBuilder
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    /**
     * Get text from a PDF file
     * @param filePath the path to the file to get text from
     * @return the string contents
     */
    private StringBuilder readPdfFile(String filePath) {
        // Init new string builder to store string
        StringBuilder content = new StringBuilder();
        // Use try statement to make sure PDFDocument is closed automatically
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            // Init a PDFTextStripper
            PDFTextStripper stripper = new PDFTextStripper();
            // Extract text using PDFTextStripper and append to StringBuilder
            content.append(stripper.getText(document));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    /**
     * Get text from a DOCX file
     * @param filePath the path to the file to get text from
     * @return the string contents of the file
     */
    private StringBuilder readDocxFile(String filePath) {
        // Init StringBuilder to store string
        StringBuilder content = new StringBuilder();
        // Use try statement to ensure input stream is closed automatically
        try (InputStream fis = new FileInputStream(filePath)) {
            // Load the DOCX document from the input stream
            XWPFDocument document = new XWPFDocument(fis);
            // Loop through each paragraph and append it to the StringBuilder
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                content.append(paragraph.getText()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    /**
     * Get text from a DOC file
     * @param filePath the path to the file to get text from
     * @return the string contents of the file
     */
    private StringBuilder readDocFile(String filePath) {
        // Init StringBuilder to store string
        StringBuilder content = new StringBuilder();
        // Use try statement so input stream is closed automatically
        try (InputStream fis = new FileInputStream(filePath)) {
            // Load the document from the input stream
            HWPFDocument document = new HWPFDocument(fis);
            // Get the entire text range from the document
            Range range = document.getRange();
            // Append it to the string builder
            content.append(range.text());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    /**
     * Get the file extension. Used to decide which of the read functions to use
     * @param filePath the path to the file you need the extension off
     * @return the string of the extension
     */
    private String getFileExtension(String filePath) {
        // Find the index of the last dot in the string
        int lastDotIndex = filePath.lastIndexOf('.');
        // Return everything after the last dot
        return filePath.substring(lastDotIndex + 1);

    }
}
