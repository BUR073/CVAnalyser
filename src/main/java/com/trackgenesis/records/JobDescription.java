package com.trackgenesis.records;

import java.util.Set;

public record JobDescription(
        Set<String> people,
        Set<String> locations,
        Set<String> organizations,
        Set<String> dates,
        Set<String> times
) {}