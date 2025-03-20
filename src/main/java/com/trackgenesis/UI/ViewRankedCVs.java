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
        // Get the job description record
        JobDescriptionRecord jdRecord = this.recordRepo.getJobDescriptionRecord();
        // Get the CV records
        List<CVRecord> cvRecords = this.recordRepo.getCVRecord();
        // Init CVRanking class and pass the job description record
        CVRanking rank = new CVRanking(jdRecord);

        // Check that there is a job description and cv record
        if (jdRecord != null && cvRecords != null) {
            // Init a new Array list to store CVScore objects
            List<CVScore> cvScores = new java.util.ArrayList<>();
            // Loop through the CVrecords
            for (CVRecord cvRecord : cvRecords) {
                // Calc CV score and add it to the cvScores list
                cvScores.add(rank.calculateCVScore(cvRecord));
            }

            // Sort by score (highest first)
            cvScores.sort(Comparator.comparingInt(CVScore::score).reversed());

            // Set initial values for column widths
            int maxNameWidth = 4;
            int maxPhoneWidth = 12;
            int maxEmailWidth = 5;
            int maxFileNameWidth = 9;
            int maxScoreWidth = 5;

            // Loop through CVScores and find max width for each column for proper formatting
            for (CVScore cvScore : cvScores) {
                maxNameWidth = Math.max(maxNameWidth, cvScore.name().length());
                maxPhoneWidth = Math.max(maxPhoneWidth, cvScore.phoneNumber().length());
                maxEmailWidth = Math.max(maxEmailWidth, cvScore.email().length());
                maxFileNameWidth = Math.max(maxFileNameWidth, cvScore.fileName().length());
            }
            // Add 2 to all widths to make table more readable, leaves some white space
            maxNameWidth += 2; maxPhoneWidth += 2; maxEmailWidth += 2; maxFileNameWidth += 2; maxScoreWidth += 2;

            // Print out table headers
            System.out.printf("%-" + (maxNameWidth) + "s %-" + (maxPhoneWidth) + "s %-" + (maxEmailWidth) + "s %-" + (maxFileNameWidth) + "s %-" + (maxScoreWidth) + "s%n",
                    "Name", "Phone Number", "Email", "File Name", "Score");
            System.out.println("-".repeat(maxNameWidth + maxPhoneWidth + maxEmailWidth + maxFileNameWidth + maxScoreWidth + 12));

            // Loop through CVScores
            for (CVScore cvScore : cvScores) {
                // Print out table row
                System.out.printf("%-" + (maxNameWidth) + "s %-" + (maxPhoneWidth) + "s %-" + (maxEmailWidth) + "s %-" + (maxFileNameWidth) + "s %-" + (maxScoreWidth) + "d%n",
                        cvScore.name(), cvScore.phoneNumber(), cvScore.email(), cvScore.fileName(), cvScore.score());
            }
            System.out.println("\nRanking Complete. Note: This program can make mistakes, it should not be used instead of manual CV\nreview but a tool to help.");
        } else {
            System.err.println("No records found");
        }
    }



}

