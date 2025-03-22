// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.CVScore;
import com.trackgenesis.records.JobDescriptionRecord;



import java.util.Set;

/**
 * Class to rank a CV against the job description
 * @author henryburbridge
 */
public class CVRanking {
    private final JobDescriptionRecord job;
    private final double skillWeight;
    private final double organizationWeight;
    private final double contactWeight;

    /**
     * Constructor
     * Defines the score weighting
     * @param job JobDescriptionRecord object
     */
    public CVRanking(JobDescriptionRecord job) {
        this.job = job;
        // Set the weighting for the scoring
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
        // Get data from CVRecord object
        String fileName = cv.fileName();
        String name = cv.people();
        String phoneNumber = cv.phoneNumber();
        String email = cv.email();
        // Calculate scores
        double skillScore = calculateSkillScore(cv.skills(), this.job.skills());
        double organizationScore = calculateOrganizationScore(cv.organizations(), this.job.organizations());
        double contactScore = calculateContactScore(cv.email(), cv.phoneNumber());

        // Calculate full score with weighting
        double rawScore = (skillScore * this.skillWeight) +
                (organizationScore * this.organizationWeight) +
                (contactScore * this.contactWeight);

        // Normalize to 0-100
        int score = (int) Math.round(rawScore * 100);
        // Return a CVScore record object
        return new CVScore(fileName, score, name, phoneNumber, email);
    }

    /**
     * Calculates score for skills
     * @param cvSkills Set of skills from the CV
     * @param jobSkills Set of skills from the job description
     * @return double of the score
     */
    private double calculateSkillScore(Set<String> cvSkills, Set<String> jobSkills) {
        if (jobSkills.isEmpty()) {
            return 1.0; // If no skills are required, consider it a perfect match.
        }
        int matchedSkills = 0;
        // Loop through skills in Job description
        for (String skill : jobSkills) {
            // If the CV contains the same skill
            if (cvSkills.contains(skill)) {
                // Add one to the score
                matchedSkills++;
            }
        }
        // Return percentage of number of skills that CV had out of the job description
        return (double) matchedSkills / jobSkills.size();
    }

    /**
     * Calculate organisation score
     * @param cvOrgs Set of organizations from the CV
     * @param jobOrgs Set of organizations from the job description
     * @return double of the score
     */
    private double calculateOrganizationScore(Set<String> cvOrgs, Set<String> jobOrgs) {
        // If there are no organizations in the CV
        if (jobOrgs.isEmpty()) {
            // Then the score is 0
            return 0.0;
        }
        int matchedOrgs = 0;
        // Loop through organizations in job description
        for (String org : jobOrgs) {
            // If the CV contains one of the organizations
            if (cvOrgs.contains(org)) {
                // Add 1 to score
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
        // True if there is an email
        boolean emailPresent = email != null && !email.isEmpty();
        // True if there is a phone number
        boolean phoneNumberPresent = phoneNumber != null && !phoneNumber.isEmpty();
        // Score 1 for both, 0.5 for only 1 and 0 for none
        if (emailPresent && phoneNumberPresent) {
            return 1.0;
        } else if (emailPresent || phoneNumberPresent) {
            return 0.5; //if only one form of contact is present.
        } else {
            return 0;
        }
    }


}