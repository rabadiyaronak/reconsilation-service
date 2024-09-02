package com.paymentology.reconsilation_service.service;

import com.paymentology.reconsilation_service.helper.MATCHING_RESULT;
import com.paymentology.reconsilation_service.helper.ReconciliationStrategy;
import com.paymentology.reconsilation_service.model.FileResponseMetaData;
import com.paymentology.reconsilation_service.model.ReconcileReportData;
import com.paymentology.reconsilation_service.model.Transaction;
import com.paymentology.reconsilation_service.util.CSVUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReconcileServiceImpl implements ReconcileService {

    private final ReconciliationStrategy reconciliationStrategy;

    public ReconcileServiceImpl(ReconciliationStrategy reconciliationStrategy) {
        this.reconciliationStrategy = reconciliationStrategy;
    }

    @Override
    public ReconcileReportData getReconciledetails(MultipartFile file1, MultipartFile file2) {
        List<Transaction> file1Transactions = CSVUtils.convertToBean(file1);
        List<Transaction> file2Transactions = CSVUtils.convertToBean(file2);
        FileResponseMetaData file1ResponseMetaData = FileResponseMetaData.builder()
                .fileName(file1.getOriginalFilename())
                .totalRecords(file1Transactions.size())
                .totalUnMatchedRecords(0)
                .closedMatchRecords(new ArrayList<>())
                .unMatchedRecords(new ArrayList<>())
                .build();

        FileResponseMetaData file2ResponseMetaData = FileResponseMetaData.builder()
                .fileName(file2.getOriginalFilename())
                .totalRecords(file2Transactions.size())
                .totalUnMatchedRecords(0)
                .closedMatchRecords(new ArrayList<>())
                .unMatchedRecords(new ArrayList<>())
                .build();

        Map<String, List<Transaction>> file1TransactionById = file1Transactions.stream()
                .collect(Collectors.toMap(Transaction::getId, transaction -> new ArrayList<Transaction>(List.of(transaction)),
                        (t1, t2) -> {
                            t1.addAll(t2);
                            return t1;
                        }));

        Map<String, List<Transaction>> file2TransactionById = file2Transactions.stream()
                .collect(Collectors.toMap(Transaction::getId, transaction -> new ArrayList<Transaction>(List.of(transaction)),
                        (t1, t2) -> {
                            t1.addAll(t2);
                            return t1;
                        }));

        reconcile(file1TransactionById, file2TransactionById, file1ResponseMetaData);
        reconcile(file2TransactionById, file1TransactionById, file2ResponseMetaData);

        file1ResponseMetaData.setTotalMatchedRecords(file1ResponseMetaData.getTotalRecords() - file1ResponseMetaData.getTotalUnMatchedRecords());
        file2ResponseMetaData.setTotalMatchedRecords(file2ResponseMetaData.getTotalRecords() - file2ResponseMetaData.getTotalUnMatchedRecords());

        ReconcileReportData reportData = new ReconcileReportData();
        reportData.getData().add(file1ResponseMetaData);
        reportData.getData().add(file2ResponseMetaData);
        return reportData;
    }

    private void reconcile(Map<String, List<Transaction>> file1TransactionById, Map<String, List<Transaction>> file2TransactionById, FileResponseMetaData file1ResponseMetaData) {

        for (Map.Entry<String, List<Transaction>> entry : file1TransactionById.entrySet()) {
            String key = entry.getKey();

            compareByPriority(entry.getValue(), file2TransactionById.get(key), file1ResponseMetaData);
        }
    }

    private void compareByPriority(List<Transaction> file1Transactions, List<Transaction> file2Transactions, FileResponseMetaData fileResponseMetaData) {
        if (CollectionUtils.isEmpty(file2Transactions)) {
            fileResponseMetaData.getUnMatchedRecords().addAll(file1Transactions);
            fileResponseMetaData.incrementUnMatchedRecordsBy(file1Transactions.size());
        } else {
            for (Transaction t1 : file1Transactions) {
                for (Transaction t2 : file2Transactions) {
                    MATCHING_RESULT result = reconciliationStrategy.getMatchingResult(t1, t2);
                    if (MATCHING_RESULT.CLOSE_MATCH == result) {
                        fileResponseMetaData.getClosedMatchRecords().add(t1);
                    } else if (MATCHING_RESULT.NOT_MATCH == result) {
                        fileResponseMetaData.incrementUnMatchedRecords();
                        fileResponseMetaData.getUnMatchedRecords().add(t1);
                    } else if (MATCHING_RESULT.MATCH == result) {
                        break;
                    }
                }
            }
        }
    }

}
