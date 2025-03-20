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
