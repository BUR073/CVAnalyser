package com.trackgenesis.NLP;

import java.util.List;

public class CVsNLP {

    private final List<String> folderPath;
    public CVsNLP(List<String> folderPath) {
        this.folderPath = folderPath;

    }

    public void start(){
        System.out.println(this.folderPath);
    }


}
