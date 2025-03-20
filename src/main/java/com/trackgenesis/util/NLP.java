// SID: 2408078
package com.trackgenesis.util;

import java.io.InputStream;
import java.util.Arrays;

/**
 * A utility class for the NLP classes
 * @author henryburbridge
 */
public class NLP {

    /**
     *
     * @param tokens The array of string tokens.
     * @param start  The starting index (inclusive) of the subset.
     * @param end    The ending index (exclusive) of the subset.
     * @return       A string formed by joining the specified subset of tokens with spaces,
     * or an empty string if the subset is empty or invalid.
     */
    public String reconstruct(String[] tokens, int start, int end) {
        return String.join(" ", Arrays.copyOfRange(tokens, start, end));
    }

    /**
     *
     * @param model The model too load
     * @return the loaded model
     */
    public InputStream load(String model) {
        return getClass().getClassLoader().getResourceAsStream(model);
    }
}
