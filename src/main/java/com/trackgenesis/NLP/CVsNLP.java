// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.enums.ContactType;


import java.nio.file.Paths;
import java.util.*;

import com.trackgenesis.util.FileReaderUtility;
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

/**
 * Class that uses Natural Language Processing models to parse CVs
 * @author henryburbridge
 */
public class CVsNLP {

    private final List<String> filePaths;
    private final RecordRepository recordRepo;
    private final FileReaderUtility extract;
    private final NLP nlp;
    private final FindInText findInText;


    // OpenNLP Models as class members
    private final SentenceModel sentenceModel;
    private final TokenizerModel tokenizerModel;
    private final TokenNameFinderModel personModel;
    private final TokenNameFinderModel organizationModel;
    private final TokenNameFinderModel dateModel;
    private final TokenNameFinderModel timeModel;

    /**
     * Defines objects and loads NLP models
     * @param filePaths List contain the file paths to the CVs
     * @param recordRepo RecordRepository object to store the CV data
     * @throws IOException if there are error's loading models
     */
    public CVsNLP(List<String> filePaths, RecordRepository recordRepo) throws IOException { // Added throws IOException
        this.filePaths = filePaths;
        this.recordRepo = recordRepo;
        this.extract = new FileReaderUtility();
        this.nlp = new NLP();
        this.findInText = new FindInText();

        // Load models in the constructor, as when not done they are repeatedly loaded causing a null pointer error
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin");
             InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin");
             InputStream PersonModel = this.nlp.load("models/en-ner-person.bin");
             InputStream OrganizationModel = this.nlp.load("models/en-ner-organization.bin");
             InputStream DateModel = this.nlp.load("models/en-ner-date.bin");
             InputStream TimeModel = this.nlp.load("models/en-ner-time.bin")) {

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

    /**
     * The entry point of this class
     * Loops through a file paths list and calls the extract method
     * Saves to record repository
     */
    public void start() {
        for (String filePath : this.filePaths) {
            // Print out just the filename not full path
            String fileName = String.valueOf(Paths.get(filePath).getFileName());
            System.out.printf("Extracting: %s%n", fileName);

            String text;
            text = this.extract.getText(filePath);
            this.recordRepo.saveRecord(this.NLP(text, fileName));

        }
    }


    /**
     * The NLP logic
     * @param text The text to be parsed
     * @param fileName the name of the file
     * @return Populated CVRecord object
     */
    public CVRecord NLP(String text, String fileName) {
        // Define sets here, originally defined in constructor, but that caused issues
        // when the same object was used multiple times.Every name would be associated with the same CV
        Set<String> people = new HashSet<>();
        Set<String> organizations = new HashSet<>();
        Set<String> dates = new HashSet<>();
        Set<String> times = new HashSet<>();
        Set<String> skills;
        Set<String> phoneNumbers;
        Set<String> emails;

        SentenceDetectorME sentenceDetector = new SentenceDetectorME(this.sentenceModel);
        TokenizerME tokenizer = new TokenizerME(this.tokenizerModel);
        NameFinderME personFinder = new NameFinderME(this.personModel);
        NameFinderME organizationFinder = new NameFinderME(this.organizationModel);
        NameFinderME dateFinder = new NameFinderME(this.dateModel);
        NameFinderME timeFinder = new NameFinderME(this.timeModel);

        String[] sentences = sentenceDetector.sentDetect(text);
        skills = this.findInText.skills(text);
        phoneNumbers = this.findInText.contactData(text, ContactType.PHONE);
        emails = this.findInText.contactData(text, ContactType.EMAIL);




        for (String sentence : sentences) {
            String[] tokens = tokenizer.tokenize(sentence);
            Span[] timesSpan = timeFinder.find(tokens);
            Span[] date = dateFinder.find(tokens);
            Span[] person = personFinder.find(tokens);
            Span[] organization = organizationFinder.find(tokens);

            for (Span span : timesSpan) {
                times.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Find Dates
            for (Span span : date) {
                dates.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Find People
            for (Span span : person) {
                people.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Find Organizations
            for (Span span : organization) {
                organizations.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }
        }

        // Store only the first email, phone number and email
        // These are the ones that are most likely associated with the applicant, of course will not be perfect
        String person = "";
        String phoneNumber = "";
        String email = "";

        if (!people.isEmpty()) {
            person = people.iterator().next(); // Get the first element
        }

        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            phoneNumber = phoneNumbers.iterator().next(); // Store as a String
        }


        if (emails != null && !emails.isEmpty()) {
            email = emails.iterator().next();
        }

        return new CVRecord(fileName, person, organizations, dates, times, skills, email, phoneNumber);
    }
}