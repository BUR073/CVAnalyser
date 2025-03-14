package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVsNLP;
import com.trackgenesis.records.RecordRepository;
import com.trackgenesis.util.FileSaver;

import java.util.List;

public class UploadCV {

    private final FileSaver fileSaver;
    private final RecordRepository recordRepo;

    public UploadCV(RecordRepository recordRepo) {
        this.fileSaver = new FileSaver();
        this.recordRepo = recordRepo;
    }


    public void upload() {
        System.out.println("Please choose a folder to upload\nAcceptable CV format includes: .txt, .pdf, .doc and .docx");
        List<String> folderPath = this.fileSaver.chooseFiles("Text files", "txt");
        CVsNLP cvsNLP = new CVsNLP(folderPath, this.recordRepo);
        cvsNLP.start();

    }
}
