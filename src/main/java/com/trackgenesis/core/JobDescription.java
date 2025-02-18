package com.trackgenesis.core;

import com.trackgenesis.ui.Menu;
import com.trackgenesis.util.IntegerUtil;
import com.trackgenesis.util.SystemUtil;
import com.trackgenesis.util.FileUtil;

import java.io.*;
import java.util.Scanner;
import java.io.File;




public class JobDescription {

    public static void type() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the job description (one line or multiple lines, end with a blank line):");

        StringBuilder jobDescription = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) { // Read until a blank line
            jobDescription.append(line).append("\n");
        }

        String filePath = FileUtil.getJobDescriptionFilePath(); // Get the file path (see explanation below)

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jobDescription.toString());
            writer.flush();
            System.out.println("Job description saved successfully\n");
        } catch (IOException e) {
            System.err.println("Error writing job description to file: " + e.getMessage());
        }
    }



    public static void viewJobDescription() {
        String filePath = FileUtil.getJobDescriptionFilePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Job Description:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading job description file: " + e.getMessage());
        }
    }

    public static void checkIfJobDescriptionExists() {
        File file = new File(FileUtil.getJobDescriptionFilePath());
        if (file.length() != 0) {
            System.out.println("Job description file exists\n Would you like to overwrite it? (y/n)");
            Scanner scanner = new Scanner(System.in);
            String overwrite = scanner.nextLine();
            if (overwrite.equalsIgnoreCase("y")) {
                try {
                    SystemUtil.emptyFile(FileUtil.getJobDescriptionFilePath());
                } catch (IOException e) {
                    System.err.println("Error clearing file");
                }
            } else if (overwrite.equalsIgnoreCase("n")) {
                System.out.println("Would you like to view the job description or return to the menu?");
                System.out.println("1. View job description");
                System.out.println("2. Return to the menu");
                System.out.println("Enter your choice: ");
                int choice = IntegerUtil.getIntegerInput(scanner);

                switch (choice) {
                    case 1:
                        Scanner scannerWait = new Scanner(System.in);
                        viewJobDescription();
                        System.out.println("\n\nPress enter to return to the menu...\n\n");
                        scannerWait.nextLine();
                        Menu.showMenu();


                    case 2:
                        Menu.showMenu();
                        break;
                }
            }
        }
    }



    public static void upload() {
        System.out.println("\n\nUpload Job Description");
        checkIfJobDescriptionExists();
        Scanner scanner = new Scanner(System.in);
        System.out.println("How would you like to upload?");
        System.out.println("1. Type into terminal");
        System.out.println("2. Upload File");
        System.out.println("3. Exit");
        System.out.println("Enter choice: ");

        int choice = IntegerUtil.getIntegerInput(scanner);
        switch (choice) {
            case 1:
                System.out.println("Type into terminal");
                type();


            case 2:
                System.out.println("Upload File");
                try {
                    File[] selectedFiles = FileUtil.chooseFilePaths();
                    if (selectedFiles != null && selectedFiles.length > 1) {
                        System.out.println("Only 1 file is needed. The first selected file will be used.");
                        File jobDescriptionSelectedFile = selectedFiles[0];
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }

            case 3:
                Menu.showMenu();

        }

    }
}
