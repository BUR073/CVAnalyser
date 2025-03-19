package com.trackgenesis.util;

import java.io.InputStream;
import java.util.Arrays;

public class NLP {

    public String reconstruct(String[] tokens, int start, int end) {
        return String.join(" ", Arrays.copyOfRange(tokens, start, end));
    }

    public InputStream load(String model) {
        return getClass().getClassLoader().getResourceAsStream(model);
    }
}
