package com.trackgenesis.UI;

import com.trackgenesis.records.RecordRepository;

public class ViewRankedCVs {
    private final RecordRepository recordRepo;
    public ViewRankedCVs(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }
    public void view(){
        System.out.println("ViewRankedCVs.view");
        System.out.println(this.recordRepo.getJobDescriptionRecord());
    }
}
