package com.trackgenesis.records;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private List<JobDescriptionRecord> JobDescriptionrecords = new ArrayList<>();
    private List<CVRecord> CVRecords = new ArrayList<>();

    public void addJobDescriptionRecord(JobDescriptionRecord record) {
        JobDescriptionrecords.add(record);
    }

    public List<JobDescriptionRecord> getJobDescriptionRecord() {
        return new ArrayList<>(JobDescriptionrecords); // Return a copy to prevent modification
    }

    public void addCVRecord(CVRecord record) {
        CVRecords.add(record);
    }

    public List<CVRecord> getCVRecord() {
        return new ArrayList<>(CVRecords);
    }

}
