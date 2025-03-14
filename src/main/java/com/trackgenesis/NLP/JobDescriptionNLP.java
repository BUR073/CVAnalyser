// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.FileExtractor;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.NLPUtil;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JobDescriptionNLP {


    private final Set<String> people = new HashSet<>();
    private final Set<String> locations = new HashSet<>();
    private final Set<String> organizations = new HashSet<>();
    private final Set<String> dates = new HashSet<>();
    private final Set<String> times = new HashSet<>();

    private final String text;
    private NLPUtil nlpUtil;

    public JobDescriptionNLP(GetProperties getProperties) {
        String filePath = getProperties.get("job.description.save.location.full.path", "properties/file.properties");
        FileExtractor extractor = new FileExtractor();
        this.text = extractor.getText(filePath);
        this.nlpUtil = new NLPUtil();


    }

    public JobDescriptionRecord extractInformation() throws IOException {
        // Sentence Detection
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin")) {
            if (sentenceModelIn == null) {
                throw new IOException("en-sent.bin not found");
            }
            SentenceModel sentenceModel = new SentenceModel(sentenceModelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            String[] sentences = sentenceDetector.sentDetect(this.text);

            // Tokenization
            try (InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin")) {
                if (tokenizerModelIn == null) {
                    throw new IOException("en-token.bin not found");
                }
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                TokenizerME tokenizer = new TokenizerME(tokenizerModel);


                try (InputStream PersonModel = this.nlpUtil.load("models/en-ner-person.bin");
                     InputStream LocationModel = this.nlpUtil.load("models/en-ner-location.bin");
                     InputStream OrganizationModel = this.nlpUtil.load("models/en-ner-organization.bin");
                     InputStream DateModel = this.nlpUtil.load("models/en-ner-date.bin");
                     InputStream TimeModel = this.nlpUtil.load("models/en-ner-time.bin")

                ) {
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
                        Span[] dateSpans = dateFinder.find(tokens);
                        Span[] personSpans = personFinder.find(tokens);
                        Span[] locationSpans = locationFinder.find(tokens);
                        Span[] organizationSpans = organizationFinder.find(tokens);

                        for (Span span : timesSpans) {
                            this.times.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find Dates
                        for (Span span : dateSpans) {
                            this.dates.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find People
                        for (Span span : personSpans) {
                            this.people.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find Locations
                        for (Span span : locationSpans) {
                            this.locations.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find Organizations
                        for (Span span : organizationSpans) {
                            this.organizations.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }
                    }

                    // Return the reference to the record with the parsed data
                    return new JobDescriptionRecord(this.people, this.locations, this.organizations, this.dates, this.times);

                }
            }
        }
    }


}