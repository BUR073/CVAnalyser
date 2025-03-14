package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CVsNLP {

    private final List<String> filePaths;
    private final RecordRepository recordRepo;
    private final FileExtractor extract;
    private String text;

    public CVsNLP(List<String> filePaths, RecordRepository recordRepo) {
        this.filePaths = filePaths;
        this.recordRepo = recordRepo;
        this.extract = new FileExtractor();
    }

    public void start() {
        for (String filePath : this.filePaths) { // Iterate over the filePaths list
            System.out.println("Extracting: " + filePath);
            this.NLP(this.extract.getText(filePath)); // Use the current filePath
        }
    }

    private void NLP(String text){
        

    }

}
