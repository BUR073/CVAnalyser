package com.trackgenesis.UI;

import com.trackgenesis.NLP.CVsNLP;
import com.trackgenesis.util.FileSaver;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class UploadCV {

    private final FileSaver fileSaver;
    private List<String> folderPath;

    public UploadCV() throws IOException {
        this.fileSaver = new FileSaver();
    }


    public void upload() {
        System.out.println("Please choose a folder to upload\nAcceptable CV format includes: .txt, .pdf, .doc and .docx");
        this.folderPath = this.fileSaver.chooseFiles("Text files", "txt");
        CVsNLP cvsNLP = new CVsNLP(this.folderPath);
        cvsNLP.start();



    }
}
