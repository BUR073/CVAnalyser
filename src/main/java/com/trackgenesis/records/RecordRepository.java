package com.trackgenesis.records;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private final List<JobDescriptionRecord> JobDescriptionRecords = new ArrayList<>();
    private final List<CVRecord> CVRecords = new ArrayList<>();

    public <T> void saveRecord(T record) {
        if (record instanceof JobDescriptionRecord) {
            JobDescriptionRecords.add((JobDescriptionRecord) record);
        } else if (record instanceof CVRecord) {
            CVRecords.add((CVRecord) record);
        } else {
            System.err.println("Unsupported record type: " + record.getClass().getName());
        }
    }

    public JobDescriptionRecord getJobDescriptionRecord() {
        if (!JobDescriptionRecords.isEmpty()) {
            return JobDescriptionRecords.get(0);
        } else {
            System.err.println("No job description record found. Please upload a job description first.");
            return null; // Or throw an exception if appropriate
        }
    }



    public List<CVRecord> getCVRecord() {
        if (!CVRecords.isEmpty()) {
            return new ArrayList<>(CVRecords);
        } else {
            System.err.println("No cv record found. Please upload a cv first.");
            return null;
        }
    }

}
