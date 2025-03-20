package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVRanking;
import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ViewRankedCVs {
    private final RecordRepository recordRepo;

    public ViewRankedCVs(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;


    }
    public void view() {
        JobDescriptionRecord jdRecord = this.recordRepo.getJobDescriptionRecord();
        List<CVRecord> cvRecords = this.recordRepo.getCVRecord();

        if (jdRecord != null && cvRecords != null) {
            List<Pair<String, Integer>> scoreList = getPairs(jdRecord, cvRecords);

            // Print the results in a formatted table
            System.out.println("----------------------------------------");
            System.out.printf("%-30s | %-5s\n", "File Name", "Score");
            System.out.println("----------------------------------------");

            for (Pair<String, Integer> pair : scoreList) {
                String fileName = pair.getKey();
                int score = pair.getValue();
                System.out.printf("%-30s | %-5d\n", fileName, score);
            }

            System.out.println("\nRanking Complete. Note: This program can make mistakes, it should not be used instead of manual CV\nreview but a tool to help.");
        } else {
            System.err.println("No records found");
        }
    }

    private List<Pair<String, Integer>> getPairs(JobDescriptionRecord jdRecord, List<CVRecord> cvRecords) {
        CVRanking rank = new CVRanking(this.recordRepo, jdRecord);
        List<Pair<String, Integer>> scoreList = new ArrayList<>();

        for (CVRecord cvRecord : cvRecords) {
            Pair<String, Integer> output = rank.calculateCVScore(cvRecord);
            scoreList.add(output);
        }

        // Sort by score in descending order
        scoreList.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2) {
                return p2.getValue().compareTo(p1.getValue()); // Descending order
            }
        });
        return scoreList;
    }

}

