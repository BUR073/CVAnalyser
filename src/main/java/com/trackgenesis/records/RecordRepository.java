// SID: 2408078
package com.trackgenesis.records;

import com.trackgenesis.util.KeyboardReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordRepository {

    private final List<JobDescriptionRecord> JobDescriptionRecords = new ArrayList<>();
    private final List<CVRecord> CVRecords = new ArrayList<>();
    private String jobDescriptionText;
    private KeyboardReader kbr;

    public RecordRepository() {
        this.kbr = new KeyboardReader();
    }
    /**
     * Saves a record to the appropriately based on it's type.
     *
     * @param <T> The type of record to save
     * @param record The record to be saved
     */
    public <T> void saveRecord(T record) {
        // Check if record exists
        if (record == null) {
            System.err.println("Attempted to save null record");
            return;
        }
        // If it is a JobDescriptionRecord
        if (record instanceof JobDescriptionRecord) {
            // If a job description record already exists
            if (!JobDescriptionRecords.isEmpty()) {
                // If the user wants to overwrite it
                if (this.confirmOverwrite()){
                    // Clear the list
                    this.JobDescriptionRecords.clear();
                    // Save the new record
                    JobDescriptionRecords.add((JobDescriptionRecord) record);
                    System.out.println("New job description successfully saved.");
                } else {
                    System.out.println("New Job description not saved. Original left.");
                }
            } else {
                // If no job description already exists, save the new one
                JobDescriptionRecords.add((JobDescriptionRecord) record);
            }

        // If it is a CVRecord
        } else if (record instanceof CVRecord) {
            // Save CVRecord
            CVRecords.add((CVRecord) record);
        } else {
            System.err.println("Unsupported record type: " + record.getClass().getName());
        }
    }

    /**
     * Function that handles user input for whether they want to overwrite a record or not
     * @return true if user want's to overwrite, false if not
     */
    private boolean confirmOverwrite() {
        String choice;
        // Setup loop so that the user must input a correct input to move on
        while (true) {
            // Get the user's choice
            choice = kbr.getString("A Job Description has already been saved. Would you like to overwrite it? (Y/N)");
            // If yes return true
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                return true;
            // If no return false
            } else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Invalid choice. Please enter Y or N.");
            }
        }
    }

    /**
     * Retrieves the first job description record if available.
     *
     * @return The first job description record or null if none exists
     */
    public JobDescriptionRecord getJobDescriptionRecord() {
        // Check that the list isn't empty
        if (!JobDescriptionRecords.isEmpty()) {
            // Return the first in the list, as you can only have one job description
            return JobDescriptionRecords.get(0);
        } else {
            System.err.println("No job description record found. Please upload a job description first.");
            return null; // Or throw an exception if appropriate
        }
    }

    /**
     * Retrieves all CV records if available.
     *
     * @return A list of CV records or an empty list if none exist
     */
    public List<CVRecord> getCVRecord() {
        // If there are CVRecords saved
        if (!CVRecords.isEmpty()) {
            // Return the entire list
            return List.copyOf(CVRecords);
        } else {
            // If not, print error and return empty list
            System.err.println("No cv record found. Please upload a cv first.");
            return Collections.emptyList();
        }
    }

    /**
     * Getter method to retun job description as a string
     * @return Job Description as a String
     */
    public String getJobDescriptionText(){
        return jobDescriptionText;
    }

    /**
     * Setter method for setting the job description as a string
     * @param jobDescriptionText the String version of the job description
     */
    public void setJobDescriptionText(String jobDescriptionText){
        this.jobDescriptionText = jobDescriptionText;
    }
}