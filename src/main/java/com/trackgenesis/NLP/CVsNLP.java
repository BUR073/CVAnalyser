// SID: 2408078
package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.enums.ContactType;


import java.nio.file.Paths;
import java.util.*;

import com.trackgenesis.util.FileReaderUtility;
import com.trackgenesis.util.FindInText;
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
        // Use a try statement so that they are closed automatically
        try (InputStream sentenceModelIn = getClass().getClassLoader().getResourceAsStream("models/en-sent.bin");
             InputStream tokenizerModelIn = getClass().getClassLoader().getResourceAsStream("models/en-token.bin");
             InputStream PersonModel = this.nlp.load("models/en-ner-person.bin");
             InputStream OrganizationModel = this.nlp.load("models/en-ner-organization.bin");
             InputStream DateModel = this.nlp.load("models/en-ner-date.bin");
             InputStream TimeModel = this.nlp.load("models/en-ner-time.bin")) {

            // Check that all the models are loaded correctly
            if (sentenceModelIn == null || tokenizerModelIn == null || PersonModel == null || OrganizationModel == null || DateModel == null || TimeModel == null) {
                throw new IOException("One or more NLP models not found");
            }

            // Create a sentence model object
            this.sentenceModel = new SentenceModel(sentenceModelIn);
            // Creat a tokenizer model object
            this.tokenizerModel = new TokenizerModel(tokenizerModelIn);
            // Create token name finder model objects for person, organization, data and time
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
        // Loop through the list of file path's
        for (String filePath : this.filePaths) {
            // Print out just the file name, not the full path
            String fileName = String.valueOf(Paths.get(filePath).getFileName());
            System.out.printf("Extracting: %s%n", fileName);

            // Get the text from the file
            String text = this.extract.getText(filePath);
            // Call the NLP method, pass in the text and save in record repository
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

        // Init SentenceDetectorMe model
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(this.sentenceModel);
        // Init TokenizerMe model
        TokenizerME tokenizer = new TokenizerME(this.tokenizerModel);
        // Init NameFinderMe models for person, organization, time and date's
        NameFinderME personFinder = new NameFinderME(this.personModel);
        NameFinderME organizationFinder = new NameFinderME(this.organizationModel);
        NameFinderME dateFinder = new NameFinderME(this.dateModel);
        NameFinderME timeFinder = new NameFinderME(this.timeModel);

        // Break text into sentences
        String[] sentences = sentenceDetector.sentDetect(text);
        // Find skills in text
        skills = this.findInText.skills(text);
        // Find phone numbers and emails in text
        phoneNumbers = this.findInText.contactData(text, ContactType.PHONE);
        emails = this.findInText.contactData(text, ContactType.EMAIL);



        // Loop through sentences
        for (String sentence : sentences) {
            // Tokenize sentence
            String[] tokens = tokenizer.tokenize(sentence);
            // Find time spans in text
            Span[] timesSpan = timeFinder.find(tokens);
            // Find date spans in text
            Span[] date = dateFinder.find(tokens);
            // Find person spans in text
            Span[] person = personFinder.find(tokens);
            // Find organization spans in text
            Span[] organization = organizationFinder.find(tokens);

            // Loop through time spans
            for (Span span : timesSpan) {
                // Reconstruct spans and save
                times.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Loop through date spans
            for (Span span : date) {
                // Reconstruct spans and save
                dates.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Loop through person spans
            for (Span span : person) {
                // Reconstruct spans and save
                people.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }

            // Loop though organization spans
            for (Span span : organization) {
                // Reconstruct spans and save
                organizations.add(this.nlp.reconstruct(tokens, span.getStart(), span.getEnd()));
            }
        }

        // Store only the first email, phone number and email
        // These are the ones that are most likely associated with the applicant, of course will not be perfect
        String person = "";
        String phoneNumber = "";
        String email = "";

        // Check people is not empty
        if (!people.isEmpty()) {
            // Store the first element
            person = people.iterator().next();
        }

        // Check phone numbers have been found
        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            // Store the first element
            phoneNumber = phoneNumbers.iterator().next();
        }

        // Check emails have been found
        if (emails != null && !emails.isEmpty()) {
            // Store the first element
            email = emails.iterator().next();
        }

        // Return CVRecord object with found data inside
        return new CVRecord(fileName, person, organizations, dates, times, skills, email, phoneNumber);
    }
}