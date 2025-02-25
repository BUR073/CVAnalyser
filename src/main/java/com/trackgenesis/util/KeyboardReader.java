package com.trackgenesis.util;
import java.util.Scanner;



/**
 * A helper class to enable input of data from the keyboard ensuring exceptions are handled here.
 *
 * @author Nigel Edwards
 *
 */
public class KeyboardReader {

    private Scanner kbr;

    /**
     * Constructor -  instantiate a scanner
     */
    public KeyboardReader()
    {
        kbr = new Scanner(System.in);
    }

    /**
     * Should only be used when we no longer need the reader
     */
    public void close()
    {
        kbr.close();
    }

    /**
     * Parses the input string to an integer - only accepts valid integers.
     *
     * @param mes - message to display to the user
     * @return - Integer entered by the user
     */
    public int getInt(String mes)
    {
        int retv = 0;
        boolean valid = false;

        while (!valid)
        {
            try
            {
                System.out.print(mes +": ");
                String input = kbr.nextLine();
                retv = Integer.parseInt(input);
                valid = true;
            }
            catch (NumberFormatException ex)
            {
                //ex.printStackTrace();
                //System.out.println(ex.getMessage());
                System.out.println("Please enter a valid Integer");
            }
        }

        return retv;
    }


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

    public int getInt(String mes, int min, int max) {

        int retv = 0;
        boolean valid = false;

        while (!valid)
        {
            retv = getInt(mes);
            if (retv >= min && retv <= max)
                valid = true;
            else
            {
                System.out.println("Please enter an Integer between " + min + " and " + max);
            }
        }
        return retv;
    }


    /**
     *
     * @param mes - Message to display
     * @return - Data entered at keyboard by user
     */
    public String getString(String mes)
    {
        System.out.print(mes +": ");
        return  kbr.nextLine();
    }

    public void newLine(){
        System.out.print("\n");
    }
}
