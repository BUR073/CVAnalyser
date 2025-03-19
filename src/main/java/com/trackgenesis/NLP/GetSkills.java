// SID: 2408078
package com.trackgenesis.NLP;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetSkills {

    private final List<String> skillsList;

    public GetSkills() throws IOException {
        String jsonFilePath = "/Users/henryburbridge/CVAnalyser/src/main/resources/skills.json";
        skillsList = loadSkillsFromJson(jsonFilePath);
    }

    public List<String> loadSkillsFromJson(String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);
        List<String> loadedSkills = new ArrayList<>();

        if (!file.exists()) {
            throw new IOException("JSON file not found: " + jsonFilePath);
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
            throw new IOException("Error reading JSON file: " + e.getMessage());
        }
    }

    public List<String> extract(String text) {
        List<String> matchedSkills = new ArrayList<>();
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

}

