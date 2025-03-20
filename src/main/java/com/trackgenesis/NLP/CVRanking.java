package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import org.apache.commons.math3.util.Pair;

import java.util.Set;

public class CVRanking {
    private JobDescriptionRecord job;
    private final double skillWeight;
    private final double organizationWeight;
    private final double contactWeight;

    public CVRanking(RecordRepository recordRepo, JobDescriptionRecord job) {
        this.job = job;
        this.skillWeight = 0.6;
        this.organizationWeight = 0.2;
        this.contactWeight = 0.1;
    }
    public Pair<String, Integer> calculateCVScore(CVRecord cv) {
        String fileName = cv.fileName();
        double skillScore = calculateSkillScore(cv.skills(), this.job.skills());
        double organizationScore = calculateOrganizationScore(cv.organizations(), this.job.organizations());
        double contactScore = calculateContactScore(cv.email(), cv.phoneNumber());

        double rawScore = (skillScore * this.skillWeight) +
                (organizationScore * this.organizationWeight) +
                (contactScore * this.contactWeight);

        // Normalize to 0-100
        int score = (int) Math.round(rawScore * 100);
        return new Pair<>(fileName, score);
    }

    private double calculateSkillScore(Set<String> cvSkills, Set<String> jobSkills) {
        if (jobSkills.isEmpty()) {
            return 1.0; // If no skills are required, consider it a perfect match.
        }
        int matchedSkills = 0;
        for (String skill : jobSkills) {
            if (cvSkills.contains(skill)) {
                matchedSkills++;
            }
        }
        return (double) matchedSkills / jobSkills.size();
    }

    private double calculateOrganizationScore(Set<String> cvOrgs, Set<String> jobOrgs) {
        if (jobOrgs.isEmpty()) {
            return 0.0;
        }
        int matchedOrgs = 0;
        for (String org : jobOrgs) {
            if (cvOrgs.contains(org)) {
                matchedOrgs++;
            }
        }
        return (double) matchedOrgs / jobOrgs.size();
    }


    private double calculateContactScore(String email, String phoneNumber) {
        if (email != null && !email.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()) {
            return 1.0;
        } else {
            return 0.5; //if only one form of contact is present.
        }
    }


}