// SID: 2408078
package com.trackgenesis.NLP;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackgenesis.enums.ContactType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class used to find specific things in a String
 * @author henryBurbridge
 */
public class FindInText {

    private final HashSet<String> skillsList;
    private final String jsonFilePath;

    /**
     * Constructor
     * Defines the file path for the JSON that contains the skills
     */
    public FindInText() {
        this.jsonFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/skills.json";
        // Create a temp list
        List<String> skillsListTemp;
        // Try and load skill's from JSON
        try {
            skillsListTemp = loadSkillsFromJson();
        // If there is an error
        } catch (IOException e) {
            // Set skillsListTemp to an empty array
            skillsListTemp = new ArrayList<>();
        }
        // Set skillsList too skillsListTemp
        // If a file not loaded correctly, this will be empty.
        // Means no skills we be found, but the program will still run
        // Convert to a hash set is it will be more efficient to search (O(1) search)
        this.skillsList = new HashSet<>(skillsListTemp);

    }

    /**
     * Loads the skills from the JSON file
     * @return a list of strings which contain the skills from the JSON file
     */
    public List<String> loadSkillsFromJson() throws IOException {
        // Init new ObjectMapper object
        ObjectMapper objectMapper = new ObjectMapper();
        // Load the file from the path
        File file = new File(this.jsonFilePath);
        // Create arraylist to store found skills
        List<String> loadedSkills = new ArrayList<>();

        // Check file exists
        if (!file.exists()) {
            System.err.println("JSON file not found: " + this.jsonFilePath);
        }

        try {
            // Read the JSON file and parse it into JsonNode tree
            JsonNode root = objectMapper.readTree(file);
            // Get the skill's node
            JsonNode skillsNode = root.get("skills");

            // Check there are skills and it is an array
            if (skillsNode != null && skillsNode.isArray()) {
                // Loop through eack skill and add it to the loadedSkills array
                skillsNode.forEach(skill -> loadedSkills.add(skill.asText()));
                // Return the array
                return loadedSkills;
            } else {
                // If there is an error finding the skills
                throw new IOException("Invalid JSON format: 'skills' field missing or not an array.");
            }
        } catch (IOException e) {
            // If there is an error loading the file
            throw new IOException("Error reading JSON file: " + e.getMessage());
        }
    }

    /**
     * Finds skills in a string
     * @param text the string to search
     * @return a Set<String> containing the skills found in the text
     */
    public Set<String> skills(String text) {
        // Create new HashSet
        Set<String> matchedSkills = new HashSet<>();
        // Split text into a list of words
        String[] words = text.split("\\s+");

        // Check that skillsList exists
        if (skillsList.isEmpty()) {
            // Return an empty list.
            // User is already informed that there is an error loading the file by the loadSkillsFromJson method
            return matchedSkills;
        }

        // Loop through the words
        for (String word : words) {
            // Remove non-alphabetic chars
            String cleanedWord = word.replaceAll("[^a-zA-Z]", "");
            // Check if word is in the skill's Hash Set
            if (skillsList.contains(cleanedWord)) {
                // If it is, add it to the list
                matchedSkills.add(cleanedWord);
            }
        }

        return matchedSkills;
    }

    /**
     * Find either a phone number of email address in a string
     * @param text The text to be searched
     * @param type ContactType enum to decide which regex to use
     * @return a Set<String> containing what has been found
     */
    public Set<String> contactData(String text, ContactType type) {
        // Define a new hash set
        Set<String> contact = new HashSet<>();
        // Check that the text var exists
        if (text == null || text.isEmpty()) {
            return contact; // Return an empty set for null or empty input
        }

        String regex;
        // Switch statement to decide which regex to use depending on the ContactType enum object input
        switch (type) {
            case PHONE -> regex = "\\+?(\\d{1,3})?[-.\\s(]?(\\d{3})[-.\\s)]?(\\d{3})[-.\\s]?(\\d{4})(?:\\s*x(\\d+))?";
            case EMAIL -> regex = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}"; // Both regexes from AI
            default -> throw new IllegalArgumentException("Invalid contact type: " + type);
        }
        // Compile the regex into a pattern object
        Pattern pattern = Pattern.compile(regex);
        // Init a matcher object to find matches in the text
        Matcher matcher = pattern.matcher(text);

        // Loop through all matches found in the text
        while (matcher.find()) {
            // and add them to the contact hash set
            contact.add(matcher.group(0)); // Add the matched phone number to the set
        }

        return contact;
    }

}

