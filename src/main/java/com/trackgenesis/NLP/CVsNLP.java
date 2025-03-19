package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileExtractor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.trackgenesis.records.JobDescriptionRecord;
import com.trackgenesis.util.FileExtractor;
import com.trackgenesis.util.GetProperties;
import com.trackgenesis.util.NLPUtil;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.RegexNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.PatternSyntaxException;


public class CVsNLP {

    private final List<String> filePaths;
    private final RecordRepository recordRepo;
    private final FileExtractor extract;
    private final NLPUtil nlpUtil;

    private final Set<String> people = new HashSet<>();
    private final Set<String> organizations = new HashSet<>();
    private final Set<String> dates = new HashSet<>();
    private final Set<String> times = new HashSet<>();
    private final List<String> phoneNumbers = new ArrayList<>();
    private String emails;

    public CVsNLP(List<String> filePaths, RecordRepository recordRepo) {
        this.filePaths = filePaths;
        this.recordRepo = recordRepo;
        this.extract = new FileExtractor();
        this.nlpUtil = new NLPUtil();
    }

    public void start() {
        for (String filePath : this.filePaths) { // Iterate over the filePaths list
            System.out.println("Extracting: " + filePath);
            try {
                this.NLP(this.extract.getText(filePath));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void NLP(String text) throws IOException {
        // Sentence Detection
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin")) {
            if (sentenceModelIn == null) {
                throw new IOException("en-sent.bin not found");
            }
            SentenceModel sentenceModel = new SentenceModel(sentenceModelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(sentenceModelIn));
            String[] sentences = sentenceDetector.sentDetect(text);

            // Tokenization
            try (InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin")) {
                if (tokenizerModelIn == null) {
                    throw new IOException("en-token.bin not found");
                }
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                TokenizerME tokenizer = new TokenizerME(tokenizerModel);


                try (InputStream PersonModel = this.nlpUtil.load("models/en-ner-person.bin");
                     InputStream OrganizationModel = this.nlpUtil.load("models/en-ner-organization.bin");
                     InputStream DateModel = this.nlpUtil.load("models/en-ner-date.bin");
                     InputStream TimeModel = this.nlpUtil.load("models/en-ner-time.bin")

                ) {
                    if (PersonModel == null || OrganizationModel == null || DateModel == null || TimeModel == null) {
                        throw new IOException("One or more NER models not found");
                    }

                    TokenNameFinderModel personModel = new TokenNameFinderModel(PersonModel);
                    TokenNameFinderModel organizationModel = new TokenNameFinderModel(OrganizationModel);
                    TokenNameFinderModel dateModel = new TokenNameFinderModel(DateModel);
                    TokenNameFinderModel timeModel = new TokenNameFinderModel(TimeModel);


                    NameFinderME personFinder = new NameFinderME(personModel);
                    NameFinderME organizationFinder = new NameFinderME(organizationModel);
                    NameFinderME dateFinder = new NameFinderME(dateModel);
                    NameFinderME timeFinder = new NameFinderME(timeModel);


                    for (String sentence : sentences) {
                        System.out.println(sentence);
                        String[] tokens = tokenizer.tokenize(sentence);
                        Span[] times = timeFinder.find(tokens);
                        Span[] date = dateFinder.find(tokens);
                        Span[] person = personFinder.find(tokens);
                        Span[] organization = organizationFinder.find(tokens);

                        try {
                            Pattern regex = Pattern.compile("\\+?(\\d{1,3})?[-.\\s(]?(\\d{3})[-.\\s)]?(\\d{3})[-.\\s]?(\\d{4})(?:\\s*x(\\d+))?");
                            Matcher regexMatcher = regex.matcher(sentence);
                            while (regexMatcher.find()) { // Find all occurrences of the pattern
                                this.phoneNumbers.add(regexMatcher.group(0)); // Store the matched phone number
                            }
                        } catch (PatternSyntaxException ex) {
                            System.err.println(ex.getMessage());
                        }


                        for (Span span : times) {
                            this.times.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find Dates
                        for (Span span : date) {
                            this.dates.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find People
                        for (Span span : person) {
                            this.people.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }

                        // Find Organizations
                        for (Span span : organization) {
                            this.organizations.add(this.nlpUtil.reconstruct(tokens, span.getStart(), span.getEnd()));
                        }


                    }
                    System.out.println("Phone: " + this.phoneNumbers);
                    System.out.println("Complete. Placeholder for now....");
                }
            }
        }
    }

}
