// SID: 2408078
package com.trackgenesis.records;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordRepository {

    private final List<JobDescriptionRecord> JobDescriptionRecords = new ArrayList<>();
    private final List<CVRecord> CVRecords = new ArrayList<>();
    private String jobDescriptionText;

    /**
     * Saves a record to the appropriate variable based on its type.
     *
     * @param <T> The type of record to save
     * @param record The record to be saved
     */
    public <T> void saveRecord(T record) {
        if (record == null) {
            System.err.println("Attempted to save null record");
            return;
        }

        if (record instanceof JobDescriptionRecord) {
            JobDescriptionRecords.add((JobDescriptionRecord) record);
        } else if (record instanceof CVRecord) {
            CVRecords.add((CVRecord) record);
        } else {
            System.err.println("Unsupported record type: " + record.getClass().getName());
        }
    }

    /**
     * Retrieves the first job description record if available.
     *
     * @return The first job description record or null if none exists
     */
    public JobDescriptionRecord getJobDescriptionRecord() {
        if (!JobDescriptionRecords.isEmpty()) {
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
        if (!CVRecords.isEmpty()) {
            return List.copyOf(CVRecords);
        } else {
            System.err.println("No cv record found. Please upload a cv first.");
            return Collections.emptyList();
        }
    }

    public String getJobDescriptionText(){
        return jobDescriptionText;
    }
    public void setJobDescriptionText(String jobDescriptionText){
        this.jobDescriptionText = jobDescriptionText;
    }
}