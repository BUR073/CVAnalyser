package com.trackgenesis.NLP;

import com.trackgenesis.records.CVRecord;

import java.util.List;

public class CVsNLP {

    private final List<String> folderPath;
    public CVsNLP(List<String> folderPath) {
        this.folderPath = folderPath;

    }

    public List<CVRecord> start(){

        System.out.println(this.folderPath);
        return List.of();
    }


}
