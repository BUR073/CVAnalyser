package com.trackgenesis.utils;

import java.util.Scanner;

public class IntegerUtils {

    public static int getIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
            return 100;
        }
        return scanner.nextInt();
    }
}
