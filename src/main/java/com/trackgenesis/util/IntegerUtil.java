package com.trackgenesis.util;

import java.util.Scanner;

public class IntegerUtil {

    public static int getIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
            return 100;
        }
        return scanner.nextInt();
    }
}
