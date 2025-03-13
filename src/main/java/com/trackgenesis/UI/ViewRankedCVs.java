package com.trackgenesis.UI;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;

import java.util.List;

public class ViewRankedCVs {
    private final RecordRepository recordRepo;

    public ViewRankedCVs(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;

    }


    public void view(){
        JobDescriptionRecord jdRecord = this.recordRepo.getJobDescriptionRecord();
        List<CVRecord> cvRecords = this.recordRepo.getCVRecord();
        if (jdRecord != null && cvRecords != null) {
            System.out.println(cvRecords);
            System.out.println(jdRecord);
        }
    }

}

