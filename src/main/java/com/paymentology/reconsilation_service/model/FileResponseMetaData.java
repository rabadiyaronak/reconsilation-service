package com.paymentology.reconsilation_service.model;

import lombok.*;

import java.util.List;

@Data
@Builder
public class FileResponseMetaData {
    private String fileName;
    private long totalRecords;
    private long totalMatchedRecords;
    private long totalUnMatchedRecords;
    private List<Transaction> unMatchedRecords;
    private List<Transaction> closedMatchRecords;


    synchronized public void incrementUnMatchedRecords() {
        ++totalMatchedRecords;
    }

    synchronized public void incrementUnMatchedRecordsBy(int val) {
        totalUnMatchedRecords += val;
    }


}
