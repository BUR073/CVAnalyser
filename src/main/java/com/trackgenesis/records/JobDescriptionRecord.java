// SID: 2408078
package com.trackgenesis.records;

import java.util.HashSet;
import java.util.Set;

/**
 * Record to store data parsed from job description
 * @author henryburbridge
 *
 * @param locations location data
 * @param organizations Set of organizations
 * @param dates Set of dates
 * @param times Set of times
 * @param skills Set of skills
 */
public record JobDescriptionRecord(
        Set<String> locations,
        Set<String> organizations,
        Set<String> dates,
        Set<String> times,
        Set<String> skills
) {
    /**
     * Constructor
     * Initializes HashSets for each variable in record
     */
    public JobDescriptionRecord() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}