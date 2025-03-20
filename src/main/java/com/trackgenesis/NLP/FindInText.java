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

    private final List<String> skillsList;
    private final String jsonFilePath;

    /**
     * Constructor
     * Defines the file path for the JSON that contains the skills
     */
    public FindInText() {
        this.jsonFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/skills.json";
        skillsList = loadSkillsFromJson();
    }

    /**
     * Loads the skills from the JSON file
     * @return a list of strings which contain the skills from the JSON file
     */
    public List<String> loadSkillsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(this.jsonFilePath);
        List<String> loadedSkills = new ArrayList<>();

        if (!file.exists()) {
            System.err.println("JSON file not found: " + this.jsonFilePath);
        }

        try {
            JsonNode root = objectMapper.readTree(file);
            JsonNode skillsNode = root.get("skills");

            if (skillsNode != null && skillsNode.isArray()) {
                skillsNode.forEach(skill -> loadedSkills.add(skill.asText()));
                return loadedSkills;
            } else {
                throw new IOException("Invalid JSON format: 'skills' field missing or not an array.");
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds skills in a string
     * @param text the string to search
     * @return a Set<String> containing the skills found in the text
     */
    public Set<String> skills(String text) {
        Set<String> matchedSkills = new HashSet<>();
        String[] words = text.split("\\s+"); // Split by any whitespace

        if (skillsList == null || skillsList.isEmpty()) {
            System.err.println("Skills list is empty or not loaded.");
            return matchedSkills;
        }

        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-Z]", ""); // Remove non-alphabetic characters
            for (String skill : skillsList) {
                if (cleanedWord.equalsIgnoreCase(skill)) {
                    matchedSkills.add(skill);
                }
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
        Set<String> phoneNumber = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return phoneNumber; // Return an empty set for null or empty input
        }

        String regex;
        switch (type) {
            case PHONE -> regex = "\\+?(\\d{1,3})?[-.\\s(]?(\\d{3})[-.\\s)]?(\\d{3})[-.\\s]?(\\d{4})(?:\\s*x(\\d+))?";
            case EMAIL -> regex = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}"; // Both regexes from AI
            default -> throw new IllegalArgumentException("Invalid contact type: " + type);
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            phoneNumber.add(matcher.group(0)); // Add the matched phone number to the set
        }

        return phoneNumber;
    }

}

