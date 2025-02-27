package com.trackgenesis.NLP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class ParseJobDescription {


    private final Set<String> people = new HashSet<>();
    private final Set<String> locations = new HashSet<>();
    private final Set<String> organizations = new HashSet<>();
    private final Set<String> dates = new HashSet<>();
    private final Set<String> times = new HashSet<>();

    public ParseJobDescription() throws IOException {

        String filePath = "JobDescription/JobDescription.txt";

        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        assert fileInputStream != null;
        String text = new String(fileInputStream.readAllBytes());
            extractInformation(text);


    }

    public void extractInformation(String text) throws IOException {
        // Sentence Detection
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin")) {
            if (sentenceModelIn == null) {
                throw new IOException("en-sent.bin not found");
            }
            SentenceModel sentenceModel = new SentenceModel(sentenceModelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            String[] sentences = sentenceDetector.sentDetect(text);

            // Tokenization
            try (InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin")) {
                if (tokenizerModelIn == null) {
                    throw new IOException("en-token.bin not found");
                }
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                TokenizerME tokenizer = new TokenizerME(tokenizerModel);

                // Named Entity Recognition (Example: People, Locations, Organizations)
                try (InputStream PersonModel = getClass().getClassLoader().getResourceAsStream("models/en-ner-person.bin");

                     InputStream LocationModel = getClass().getClassLoader().getResourceAsStream("models/en-ner-location.bin");

                     InputStream OrganizationModel = getClass().getClassLoader().getResourceAsStream("models/en-ner-organization.bin");

                     InputStream DateModel = getClass().getClassLoader().getResourceAsStream("models/en-ner-date.bin");

                     InputStream TimeModel = getClass().getClassLoader().getResourceAsStream("models/en-ner-time.bin")

                )

                {
                    if (PersonModel == null || LocationModel == null || OrganizationModel == null || DateModel == null || TimeModel == null) {
                        throw new IOException("One or more NER models not found");
                    }

                    TokenNameFinderModel personModel = new TokenNameFinderModel(PersonModel);
                    TokenNameFinderModel locationModel = new TokenNameFinderModel(LocationModel);
                    TokenNameFinderModel organizationModel = new TokenNameFinderModel(OrganizationModel);
                    TokenNameFinderModel dateModel = new TokenNameFinderModel(DateModel);
                    TokenNameFinderModel timeModel = new TokenNameFinderModel(TimeModel);


                    NameFinderME personFinder = new NameFinderME(personModel);
                    NameFinderME locationFinder = new NameFinderME(locationModel);
                    NameFinderME organizationFinder = new NameFinderME(organizationModel);
                    NameFinderME dateFinder = new NameFinderME(dateModel);
                    NameFinderME timeFinder = new NameFinderME(timeModel);


                    for (String sentence : sentences) {
                        String[] tokens = tokenizer.tokenize(sentence);

                        Span[] timesSpans = timeFinder.find(tokens);
                        for (Span span : timesSpans) {
                            this.times.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }

                        // Find Dates
                        Span[] dateSpans = dateFinder.find(tokens);
                        for (Span span : dateSpans) {
                            this.dates.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }
                        // Find People
                        Span[] personSpans = personFinder.find(tokens);
                        for (Span span : personSpans) {
                            this.people.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }

                        // Find Locations
                        Span[] locationSpans = locationFinder.find(tokens);
                        for (Span span : locationSpans) {
                            this.locations.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }

                        // Find Organizations
                        Span[] organizationSpans = organizationFinder.find(tokens);
                        for (Span span : organizationSpans) {
                            this.organizations.add(String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd())));
                        }
                    }

                    System.out.println("People: " + this.people);
                    System.out.println("Locations: " + this.locations);
                    System.out.println("Organizations: " + this.organizations);
                    System.out.println("Dates: " + this.dates);
                    System.out.println("Times: " + this.times);
                }
            }
        }
    }
}