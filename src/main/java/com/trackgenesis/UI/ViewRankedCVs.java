package com.trackgenesis.UI;

import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;

public class ViewRankedCVs {
    private final RecordRepository recordRepo;
    public ViewRankedCVs(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }
    public void view(){
        JobDescriptionRecord JDRecord = this.recordRepo.getJobDescriptionRecord();
        if (JDRecord != null) {
            try{
                System.out.println(JDRecord);
            } catch (Exception e){
                System.err.println("Error displaying JobDescriptionRecord" + e.getMessage());
            }
        } else {
            System.err.println("No job description record found. Please upload a job description first.");
        }
    }

}

