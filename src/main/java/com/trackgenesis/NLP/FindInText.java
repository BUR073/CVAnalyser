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

public class FindInText {

    private final List<String> skillsList;

    public FindInText() {
        String jsonFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/skills.json";
        skillsList = loadSkillsFromJson(jsonFilePath);
    }

    public List<String> loadSkillsFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);
        List<String> loadedSkills = new ArrayList<>();

        if (!file.exists()) {
            System.err.println("JSON file not found: " + jsonFilePath);
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
                    if (!matchedSkills.contains(skill)) {
                        matchedSkills.add(skill);
                    }
                }
            }
        }

        return matchedSkills;
    }

    public Set<String> contactData(String text, ContactType type) {
        Set<String> phoneNumber = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return phoneNumber; // Return empty set for null or empty input
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

