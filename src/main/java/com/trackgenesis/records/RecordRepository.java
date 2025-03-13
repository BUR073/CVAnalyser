package com.trackgenesis.records;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private List<JobDescriptionRecord> JobDescriptionrecords = new ArrayList<>();
    private List<CVRecord> CVRecords = new ArrayList<>();

    public <T> void saveRecord(T record) {
        if (record instanceof JobDescriptionRecord) {
            JobDescriptionrecords.add((JobDescriptionRecord) record);
        } else if (record instanceof CVRecord) {
            CVRecords.add((CVRecord) record);
        } else {
            System.err.println("Unsupported record type: " + record.getClass().getName());
        }
    }

    public List<JobDescriptionRecord> getJobDescriptionRecord() {
        return new ArrayList<>(JobDescriptionrecords); // Return a copy to prevent modification
    }



    public List<CVRecord> getCVRecord() {
        return new ArrayList<>(CVRecords);
    }

}
