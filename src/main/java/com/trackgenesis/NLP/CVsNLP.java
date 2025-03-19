package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileExtractor;

import java.util.*;
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


public class CVsNLP {

    private final List<String> filePaths;
    private final RecordRepository recordRepo;
    private final FileExtractor extract;
    private final NLPUtil nlpUtil;
    private final FindSkills findSkills;

    private final Set<String> people = new HashSet<>();
    private final Set<String> organizations = new HashSet<>();
    private final Set<String> dates = new HashSet<>();
    private final Set<String> times = new HashSet<>();
    private Set<String> skills = new HashSet<>();
    private final Set<String> phoneNumbers = new HashSet<>();
    private String emails;
    private String text;

    // OpenNLP Models as class members
    private final SentenceModel sentenceModel;
    private final TokenizerModel tokenizerModel;
    private final TokenNameFinderModel personModel;
    private final TokenNameFinderModel organizationModel;
    private final TokenNameFinderModel dateModel;
    private final TokenNameFinderModel timeModel;

    public CVsNLP(List<String> filePaths, RecordRepository recordRepo) throws IOException { // Added throws IOException
        this.filePaths = filePaths;
        this.recordRepo = recordRepo;
        this.extract = new FileExtractor();
        this.nlpUtil = new NLPUtil();
        this.findSkills = new FindSkills();

        // Load models ONCE in the constructor
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin");
             InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin");
             InputStream PersonModel = this.nlpUtil.load("models/en-ner-person.bin");
             InputStream OrganizationModel = this.nlpUtil.load("models/en-ner-organization.bin");
             InputStream DateModel = this.nlpUtil.load("models/en-ner-date.bin");
             InputStream TimeModel = this.nlpUtil.load("models/en-ner-time.bin")) {

            if (sentenceModelIn == null || tokenizerModelIn == null || PersonModel == null || OrganizationModel == null || DateModel == null || TimeModel == null) {
                throw new IOException("One or more NLP models not found");
            }

            this.sentenceModel = new SentenceModel(sentenceModelIn);
            this.tokenizerModel = new TokenizerModel(tokenizerModelIn);
            this.personModel = new TokenNameFinderModel(PersonModel);
            this.organizationModel = new TokenNameFinderModel(OrganizationModel);
            this.dateModel = new TokenNameFinderModel(DateModel);
            this.timeModel = new TokenNameFinderModel(TimeModel);
        }
    }

    public void start() {
        for (String filePath : this.filePaths) {
            System.out.println("Extracting: " + filePath);
            try {
                this.text = this.extract.getText(filePath);
                this.recordRepo.saveRecord(this.NLP(this.text));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public CVRecord NLP(String text) throws IOException {
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(this.sentenceModel);
        TokenizerME tokenizer = new TokenizerME(this.tokenizerModel);
        NameFinderME personFinder = new NameFinderME(this.personModel);
        NameFinderME organizationFinder = new NameFinderME(this.organizationModel);
        NameFinderME dateFinder = new NameFinderME(this.dateModel);
        NameFinderME timeFinder = new NameFinderME(this.timeModel);

        String[] sentences = sentenceDetector.sentDetect(this.text);
        this.skills = findSkills.extract(this.text);

        for (String sentence : sentences) {
            System.out.println("Sentence: " + sentence);
            String[] tokens = tokenizer.tokenize(sentence);
            Span[] times = timeFinder.find(tokens);
            Span[] date = dateFinder.find(tokens);
            Span[] person = personFinder.find(tokens);
            Span[] organization = organizationFinder.find(tokens);

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
        System.out.println("Skills: " + this.skills);
        System.out.println("Complete.");
        return new CVRecord(this.people, this.organizations, this.dates, this.times, this.skills, null, null);
    }
}