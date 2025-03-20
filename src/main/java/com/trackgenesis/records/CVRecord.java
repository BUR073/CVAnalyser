// SID: 2408078
package com.trackgenesis.records;

import java.util.HashSet;
import java.util.Set;

/**
 * Record to store data parsed from CVs
 * @param fileName the name of the file
 * @param people Set of people
 * @param organizations Set of organizations
 * @param dates Set of dates
 * @param times Set of times
 * @param skills Set of skills
 * @param email Email address
 * @param phoneNumber Phone number
 */
public record CVRecord(
        String fileName,
        String people,
        Set<String> organizations,
        Set<String> dates,
        Set<String> times,
        Set<String> skills,
        String email,
        String phoneNumber
) {

    /**
     * Constructor
     * Initialise initial values, mostly HashSets
     */
    public CVRecord() {
        this("", "", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), null, null);
    }
}

