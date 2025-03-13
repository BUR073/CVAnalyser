package com.trackgenesis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    private final Properties properties;

    public GetProperties() {
        this.properties = new Properties();
    }

    public String get(String name, String filePath) {

        if (name == null || name.isEmpty() || filePath == null || filePath.isEmpty()) {
            System.err.println("Name and filePath cannot be null or empty.");
        }

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (input == null) {
                System.err.println("Properties file not found: " + filePath);
            }
            this.properties.load(input);
            String value = this.properties.getProperty(name);
            if(value == null){
                System.err.println("Property '" + name + "' not found in file: " + filePath);
            }
            return value;

        } catch (IOException e) {
            // Re-throw the exception to let the caller handle it or wrap it in a custom exception.
            System.err.println("Error loading properties file: " + filePath + e.getMessage());
        }
        return null;
    }
}
