// SID: 2408078
package com.trackgenesis.records;

import java.util.HashSet;
import java.util.Set;

public record CVRecord(
        Set<String> education,
        Set<String> experience,
        Set<String> skills,
        Set<String> jobTitles,
        String name,
        String email,
        int phoneNumber
) {
    public CVRecord() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), null, null, 0);
    }
}