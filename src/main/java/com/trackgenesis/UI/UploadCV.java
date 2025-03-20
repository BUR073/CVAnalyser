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
        List<String> folderPath = this.fileChooser.chooseFiles("Text files", "txt");
        try {
            CVsNLP cvsNLP = new CVsNLP(folderPath, this.recordRepo);
            cvsNLP.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
