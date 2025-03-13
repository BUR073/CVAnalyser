package com.trackgenesis.records;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private final List<JobDescriptionRecord> JobDescriptionrecords = new ArrayList<>();
    private final List<CVRecord> CVRecords = new ArrayList<>();

    public <T> void saveRecord(T record) {
        if (record instanceof JobDescriptionRecord) {
            JobDescriptionrecords.add((JobDescriptionRecord) record);
        } else if (record instanceof CVRecord) {
            CVRecords.add((CVRecord) record);
        } else {
            System.err.println("Unsupported record type: " + record.getClass().getName());
        }
    }

    public JobDescriptionRecord getJobDescriptionRecord() {
        if (!JobDescriptionrecords.isEmpty()) {
            return JobDescriptionrecords.get(0);
        } else {
            return null; // Or throw an exception if appropriate
        }
    }



    public List<CVRecord> getCVRecord() {
        return new ArrayList<>(CVRecords);
    }

}
