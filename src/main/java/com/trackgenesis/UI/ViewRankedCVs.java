package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVRanking;
import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import org.apache.commons.math3.util.Pair;

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
            CVRanking rank = new CVRanking(this.recordRepo, jdRecord);
            for (CVRecord cvRecord : cvRecords) {
                Pair<String, Integer> output = rank.calculateCVScore(cvRecord);
                System.out.println("File Name: " + output.getKey() + " | Score:  " + output.getValue());
            }
            System.out.println("\n Ranking Complete. Note: This program can make mistakes, it should not be used instead of manual CV review but a tool to help.");
        } else {
            System.err.println("No records found");
        }

    }

}

