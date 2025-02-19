package com.trackgenesis;
import com.trackgenesis.UI.Login;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/users.txt"; // Path to your users file

        Login login = new Login(filePath); // Create the object
        Scanner scanner = new Scanner(System.in); // Create the Scanner

        login.login(scanner); // Call the login method

        scanner.close(); // Close the Scanner when done!  Very important.
    }
}