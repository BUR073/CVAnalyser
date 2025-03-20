// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVRanking;
import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.CVScore;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class to view the CVs in a ranked order
 * @author henryburbridge
 */
public class ViewRankedCVs {
    private final RecordRepository recordRepo;

    /**
     * Constructor
     * @param recordRepo stores the records
     */
    public ViewRankedCVs(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }

    /**
     * Accesses jobDescriptionRecord and CVRecord via RecordRepository.
     * Displays each CVs filename and rank in descending order
     */
    public void view() {
        JobDescriptionRecord jdRecord = this.recordRepo.getJobDescriptionRecord();
        List<CVRecord> cvRecords = this.recordRepo.getCVRecord();
        CVRanking rank = new CVRanking(jdRecord);

        if (jdRecord != null && cvRecords != null) {
            List<CVScore> cvScores = new java.util.ArrayList<>();
            for (CVRecord cvRecord : cvRecords) {
                cvScores.add(rank.calculateCVScore(cvRecord));
            }

            // Sort by score (highest first)
            cvScores.sort(Comparator.comparingInt(CVScore::score).reversed());

            // Calculate maximum column widths
            int maxNameWidth = 4; // Header width
            int maxPhoneWidth = 12; // Header width
            int maxEmailWidth = 5; // Header width
            int maxFileNameWidth = 9; // Header width
            int maxScoreWidth = 5; // Header width

            for (CVScore cvScore : cvScores) {
                maxNameWidth = Math.max(maxNameWidth, cvScore.name().length());
                maxPhoneWidth = Math.max(maxPhoneWidth, cvScore.phoneNumber().length());
                maxEmailWidth = Math.max(maxEmailWidth, cvScore.email().length());
                maxFileNameWidth = Math.max(maxFileNameWidth, cvScore.fileName().length());
            }

            // Table Header
            System.out.printf("%-" + (maxNameWidth + 2) + "s %-" + (maxPhoneWidth + 2) + "s %-" + (maxEmailWidth + 2) + "s %-" + (maxFileNameWidth + 2) + "s %-" + (maxScoreWidth + 2) + "s%n",
                    "Name", "Phone Number", "Email", "File Name", "Score");
            System.out.println("-".repeat(maxNameWidth + maxPhoneWidth + maxEmailWidth + maxFileNameWidth + maxScoreWidth + 12));

            for (CVScore cvScore : cvScores) {
                System.out.printf("%-" + (maxNameWidth + 2) + "s %-" + (maxPhoneWidth + 2) + "s %-" + (maxEmailWidth + 2) + "s %-" + (maxFileNameWidth + 2) + "s %-" + (maxScoreWidth + 2) + "d%n",
                        cvScore.name(), cvScore.phoneNumber(), cvScore.email(), cvScore.fileName(), cvScore.score());
            }
            System.out.println("\nRanking Complete. Note: This program can make mistakes, it should not be used instead of manual CV\nreview but a tool to help.");
        } else {
            System.err.println("No records found");
        }
    }



}

