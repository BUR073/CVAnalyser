package com.trackgenesis;
import com.trackgenesis.UI.Menu;

import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.NLP.ParseJobDescription;
import java.io.IOException;

public class Main {

    private final Menu menu;

    public Main(Menu menu) {
        this.menu = menu;
    }

    public void start() throws IOException {
        ParseJobDescription parseJobDescription = new ParseJobDescription();
        JobDescriptionRecord jobData = parseJobDescription.extractInformation();

        System.out.println("People: " + jobData.people());
        System.out.println("Locations: " + jobData.locations());
        System.out.println("Organizations: " + jobData.organizations());
        System.out.println("Dates: " + jobData.dates());
        System.out.println("Times: " + jobData.times());

        this.menu.showMenu();
    }

    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            Main main = new Main(menu);
            main.start();
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage()); // Handle the exception

        }
    }
}