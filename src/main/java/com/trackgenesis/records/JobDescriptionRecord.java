// SID: 2408078
package com.trackgenesis.records;

import java.util.HashSet;
import java.util.Set;

public record JobDescriptionRecord(
        Set<String> people,
        Set<String> locations,
        Set<String> organizations,
        Set<String> dates,
        Set<String> times
) {
    public JobDescriptionRecord() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}