// SID: 2408078
package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVsNLP;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileChooser;

import java.util.List;

/**
 * Class to upload CVs
 * @author henryburbridge
 */
public class UploadCV {

    private final FileChooser fileChooser;
    private final RecordRepository recordRepo;

    /**
     * Constructor
     * Creates a new fileChooser object
     * @param recordRepo the repository of records
     */
    public UploadCV(RecordRepository recordRepo) {
        this.fileChooser = new FileChooser();
        this.recordRepo = recordRepo;
    }


    /**
     * Allows user to choose files to upload.
     * Passes file paths into CVsNLP class
     */
    public void upload() {
        System.out.println("Please choose file's to upload\nAcceptable CV format includes: .txt, .pdf, .doc and .docx");
        // Get a list of folder paths from fileChooser class
        List<String> folderPath = this.fileChooser.chooseFiles("Text files", "txt");
        try {
            // Init new CVsNLP Class and pass in list of folder paths and record repository object
            CVsNLP cvsNLP = new CVsNLP(folderPath, this.recordRepo);
            // Call start method
            cvsNLP.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
