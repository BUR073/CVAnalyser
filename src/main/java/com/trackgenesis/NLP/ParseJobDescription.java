package com.trackgenesis.NLP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class ParseJobDescription {

    private final String filePath;

    private String educationRequirements;
    private String skills;
    private String jobTitle;
    private String responsibilities;
    private String qualifications;

    public ParseJobDescription() throws IOException {
        Properties properties = new Properties();
        // load application file
        try (InputStream inputStream = getClass().getResourceAsStream("/application.properties")) {
            if (inputStream == null) {
                throw new IOException("application.properties not found");
            }
            properties.load(inputStream);
        }

        // Get full file path from application file
        //this.filePath = properties.getProperty("job.description.save.location")
                //+ "/" +
                //properties.getProperty("job.description.file.name")
                //+ ".txt";
        this.filePath = "JobDescription/JobDescription.txt";

        //InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(this.filePath);
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(this.filePath);
        assert fileInputStream != null;
        String text = new String(fileInputStream.readAllBytes());
            extractInformation(text);



        this.educationRequirements = "";
        this.skills = "";
        this.jobTitle = "";
        this.responsibilities = "";
        this.qualifications = "";

    }

    public void extractInformation(String text) throws IOException {
        // Sentence Detection
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("en-sent.bin")) {
            if (sentenceModelIn == null) {
                throw new IOException("en-sent.bin not found");
            }
            SentenceModel sentenceModel = new SentenceModel(sentenceModelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            String[] sentences = sentenceDetector.sentDetect(text);

            // Tokenization
            try (InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("en-token.bin")) {
                if (tokenizerModelIn == null) {
                    throw new IOException("en-token.bin not found");
                }
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                TokenizerME tokenizer = new TokenizerME(tokenizerModel);

                // Named Entity Recognition (Example: People, Locations, Organizations)
                try (InputStream nameFinderPersonModelIn = getClass().getClassLoader().getResourceAsStream("en-ner-person.bin");
                     InputStream nameFinderLocationModelIn = getClass().getClassLoader().getResourceAsStream("en-ner-location.bin");
                     InputStream nameFinderOrganizationModelIn = getClass().getClassLoader().getResourceAsStream("en-ner-organization.bin")) {
                    if (nameFinderPersonModelIn == null || nameFinderLocationModelIn == null || nameFinderOrganizationModelIn == null) {
                        throw new IOException("One or more NER models not found");
                    }

                    TokenNameFinderModel personModel = new TokenNameFinderModel(nameFinderPersonModelIn);
                    TokenNameFinderModel locationModel = new TokenNameFinderModel(nameFinderLocationModelIn);
                    TokenNameFinderModel organizationModel = new TokenNameFinderModel(nameFinderOrganizationModelIn);

                    NameFinderME personFinder = new NameFinderME(personModel);
                    NameFinderME locationFinder = new NameFinderME(locationModel);
                    NameFinderME organizationFinder = new NameFinderME(organizationModel);

                    Set<String> people = new HashSet<>();
                    Set<String> locations = new HashSet<>();
                    Set<String> organizations = new HashSet<>();

                    for (String sentence : sentences) {
                        String[] tokens = tokenizer.tokenize(sentence);

                        // Find People
                        Span[] personSpans = personFinder.find(tokens);
                        for (Span span : personSpans) {
                            people.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }

                        // Find Locations
                        Span[] locationSpans = locationFinder.find(tokens);
                        for (Span span : locationSpans) {
                            locations.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }

                        // Find Organizations
                        Span[] organizationSpans = organizationFinder.find(tokens);
                        for (Span span : organizationSpans) {
                            organizations.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }
                    }

                    System.out.println("People: " + people);
                    System.out.println("Locations: " + locations);
                    System.out.println("Organizations: " + organizations);
                }
            }
        }
    }
}