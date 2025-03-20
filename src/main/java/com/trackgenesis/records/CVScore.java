// SID: 2408078
package com.trackgenesis.records;

/**
 * Record used to return values in the CVsNLP method
 * @param fileName the name of the file for the CV
 * @param score the score the CV reached
 * @param name the name of the applicant
 * @param phoneNumber the applicant phone number
 * @param email the applicant email address
 * @author henryburbridge
 */
public record CVScore(
        String fileName,
        int score,
        String name,
        String phoneNumber,
        String email
) {
}