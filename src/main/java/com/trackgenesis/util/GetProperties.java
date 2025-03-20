// SID: 2408078
package com.trackgenesis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for getting properties from properties file
 * @author henryburbridge
 */
public class GetProperties {

    private final Properties properties;

    /**
     * Constructor
     * Defines a new properties object
     */
    public GetProperties() {
        this.properties = new Properties();
    }

    /**
     * Get a specific property from a specific properties file
     * @param name the name of the property
     * @param filePath the properties file a path
     * @return the contents of the chosen property
     */
    public String get(String name, String filePath) {
        // Check if the filepath is not null or empty
        if (name == null || name.isEmpty() || filePath == null || filePath.isEmpty()) {
            System.err.println("Name and filePath cannot be null or empty.");
        }

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            // Check if the properties file was found
            if (input == null) {
                System.err.println("Properties file not found: " + filePath);
            }
            // Load the properties
            this.properties.load(input);
            String value = this.properties.getProperty(name);
            // Check if the property was found
            if(value == null){
                System.err.println("Property '" + name + "' not found in file: " + filePath);
            }
            // Return the property
            return value;

            // Catch IO error
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + filePath + e.getMessage());
        }
        // Only reached if there was an error
        return null;
    }
}
