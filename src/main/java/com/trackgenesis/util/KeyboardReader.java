// SID: 2408078
package com.trackgenesis.util;

import java.util.Scanner;

// This Class is taken from the tutorial session, I have made minor changes, but it is on the whole, unchanged.

/**
 * A helper class to enable input of data from the keyboard ensuring exceptions are handled here.
 * Taken from tutorial sessions with minor adaptations
 * @author Nigel Edwards
 */
public class KeyboardReader {

    private final Scanner kbr;

    /**
     * Constructor -  instantiate a scanner
     */
    public KeyboardReader() {
        kbr = new Scanner(System.in);
    }

    /**
     * Should only be used when we no longer need the reader
     */
    public void close() {
        kbr.close();
    }

    /**
     * Parses the input string to an integer - only accepts valid integers.
     *
     * @param mes - message to display to the user
     * @return - Integer entered by the user
     */
    public int getInt(String mes) {
        int retv = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(mes + ": ");
                String input = kbr.nextLine();
                retv = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid Integer");
            }
        }

        return retv;
    }

    /**
     * Allows for a user to add new lines when entering a string
     * @param mes message to display to the user
     * @return the long string entered
     */
    public String getLongString(String mes) {
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println(mes);

        String line;

        while (kbr.hasNextLine()) {
            line = kbr.nextLine();
            if (line.isEmpty()) {
                break; // Exit on a single empty line
            } else {
                stringBuilder.append(line).append(System.lineSeparator());

            }

        }

        return stringBuilder.toString();
    }

    /**
     * @param mes - Message to display
     * @return - Data entered at keyboard by user
     */
    public String getString(String mes) {
        String input;
        while (true) { // Loop until valid input
            System.out.print(mes + ": ");
            input = kbr.nextLine().trim(); // Read input and trim whitespace

            if (!input.isEmpty()) { // Check for empty string
                return input; // Return valid input
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }


}
