// SID: 2408078
package com.trackgenesis.records;

import java.util.HashSet;
import java.util.Set;

public record CVRecord(
        Set<String> people,
        Set<String> organizations,
        Set<String> dates,
        Set<String> times,
        Set<String> skills,
        String email,
        String phoneNumber
) {
    public CVRecord() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), null, null);
    }
}

