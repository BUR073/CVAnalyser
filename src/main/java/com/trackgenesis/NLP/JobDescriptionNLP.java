// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.JobDescriptionRecord;

import com.trackgenesis.util.NLP;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Natural language processing class for the job description
 * @author henryburbridge
 */
public class JobDescriptionNLP {

    private final Set<String> locations = new HashSet<>();
    private final Set<String> organizations = new HashSet<>();
    private final Set<String> dates = new HashSet<>();
    private final Set<String> times = new HashSet<>();

    private final String text;
    private final NLP nlp;
    private final FindInText findInText;

    /**
     * Constructor
     * @param text String version of the job description
     */
    public JobDescriptionNLP(String text)  {
        this.text = text;
        this.nlp = new NLP();
        this.findInText = new FindInText();


    }

    /**
     * Uses NLP models to extract keywords from job description text
     * @return JobDescriptionRecord
     * @throws IOException if there are error loading models
     */
    public JobDescriptionRecord extractInformation() throws IOException {


        // Attempt to load sentence detection model
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin")) {
            // If unsuccessful
            if (sentenceModelIn == null) {
                throw new IOException("en-sent.bin not found");
            }
            // Init sentence model object
            SentenceModel sentenceModel = new SentenceModel(sentenceModelIn);
            // Init Sentence DetectorMe object
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            // Split text into sentences and store them
            String[] sentences = sentenceDetector.sentDetect(this.text);
            // Find skills in text and store in set
            Set<String> skills = findInText.skills(this.text);

            // Attempt to load tokenizer model
            try (InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin")) {
                // If unsuccessful
                if (tokenizerModelIn == null) {
                    throw new IOException("en-token.bin not found");
                }
                // Init new tokenizer model object
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                // Init new tokenizerMe object
                TokenizerME tokenizer = new TokenizerME(tokenizerModel);

                // Try to load named entity recognition (NER) models
                try (InputStream LocationModel = this.nlp.load("models/en-ner-location.bin");
                     InputStream OrganizationModel = this.nlp.load("models/en-ner-organization.bin");
                     InputStream DateModel = this.nlp.load("models/en-ner-date.bin");
                     InputStream TimeModel = this.nlp.load("models/en-ner-time.bin")

                ) {
                    // Check all NER models where loaded successfully
                    if (LocationModel == null || OrganizationModel == null || DateModel == null || TimeModel == null) {
                        throw new IOException("One or more NER models not found");
                    }

                    // Init TokenNameFinderModel objects for each NER model
                    TokenNameFinderModel locationModel = new TokenNameFinderModel(LocationModel);
                    TokenNameFinderModel organizationModel = new TokenNameFinderModel(OrganizationModel);
                    TokenNameFinderModel dateModel = new TokenNameFinderModel(DateModel);
                    TokenNameFinderModel timeModel = new TokenNameFinderModel(TimeModel);

                    // Init NameFinderMe objects for each NER model
                    NameFinderME locationFinder = new NameFinderME(locationModel);
                    NameFinderME organizationFinder = new NameFinderME(organizationModel);
                    NameFinderME dateFinder = new NameFinderME(dateModel);
                    NameFinderME timeFinder = new NameFinderME(timeModel);

                    // Loop through each sentence in the text
                    for (String sentence : sentences) {
                        // Tokenize the sentence
                        String[] tokens = tokenizer.tokenize(sentence);

                        // Span refers to the starting and end point of the named entity
                        // Find named entities in the tokens and get their spans
                        Span[] timesSpans = timeFinder.find(tokens);
                        Span[] dateSpans = dateFinder.find(tokens);
                        Span[] locationSpans = locationFinder.find(tokens);
                        Span[] organizationSpans = organizationFinder.find(tokens);

                        // Loop through the time spans
                        for (Span span : timesSpans) {
                            // Reconstruct and add to the list
                            this.times.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Loop through the date spans
                        for (Span span : dateSpans) {
                            // Reconstruct and add to the list
                            this.dates.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Loop through locations spans
                        for (Span span : locationSpans) {
                            // Reconstruct and add to the list
                            this.locations.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Loop through organization spans
                        for (Span span : organizationSpans) {
                            // Reconstruct and add to the list
                            this.organizations.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }
                    }

                    // Return JobDescriptionRecord object filled with data taken from the text
                    System.out.println(skills);
                    return new JobDescriptionRecord(this.locations, this.organizations, this.dates, this.times, skills);

                }
            }
        }
    }


}