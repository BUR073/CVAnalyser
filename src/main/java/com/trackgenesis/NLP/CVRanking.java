// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.CVScore;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.records.RecordRepository;
import org.apache.commons.math3.util.Pair;

import java.util.Set;

/**
 * Class to rank a CV against the job description
 * @author henryburbridge
 */
public class CVRanking {
    private JobDescriptionRecord job;
    private final double skillWeight;
    private final double organizationWeight;
    private final double contactWeight;

    /**
     * Constructor
     * Defines the score weighting
     * @param recordRepo RecordRepository object
     * @param job JobDescriptionRecord object
     */
    public CVRanking(RecordRepository recordRepo, JobDescriptionRecord job) {
        this.job = job;
        this.skillWeight = 0.6;
        this.organizationWeight = 0.2;
        this.contactWeight = 0.1;
    }

    /**
     * Class entry point, uses a CVScore record to return values
     * @param cv CVRecord object
     * @return Populated CVScore object
     */
    public CVScore calculateCVScore(CVRecord cv) {
        String fileName = cv.fileName();
        String name = cv.people();
        String phoneNumber = cv.phoneNumber();
        String email = cv.email();
        double skillScore = calculateSkillScore(cv.skills(), this.job.skills());
        double organizationScore = calculateOrganizationScore(cv.organizations(), this.job.organizations());
        double contactScore = calculateContactScore(cv.email(), cv.phoneNumber());

        double rawScore = (skillScore * this.skillWeight) +
                (organizationScore * this.organizationWeight) +
                (contactScore * this.contactWeight);

        // Normalize to 0-100
        int score = (int) Math.round(rawScore * 100);
        return new CVScore(fileName, score, name, phoneNumber, email);
    }

    /**
     * Calculates score for skills
     * @param cvSkills Set of skills from the CV
     * @param jobSkills Set of skills from the job descripton
     * @return double of the score
     */
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

    /**
     * Calculate organisation score
     * @param cvOrgs Set of organizations from the CV
     * @param jobOrgs Set of organizations from the job description
     * @return double of the score
     */
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

    /**
     * Calculate contact details score,
     * only used to give benefit to applicants who actually put contact details on their CV
     * @param email The application email
     * @param phoneNumber the applicant phone number
     * @return the score
     */
    private double calculateContactScore(String email, String phoneNumber) {
        boolean emailPresent = email != null && !email.isEmpty();
        boolean phoneNumberPresent = phoneNumber != null && !phoneNumber.isEmpty();
        if (emailPresent && phoneNumberPresent) {
            return 1.0;
        } else if (emailPresent || phoneNumberPresent) {
            return 0.5; //if only one form of contact is present.
        } else {
            return 0;
        }
    }


}