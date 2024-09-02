package com.paymentology.reconsilation_service.service;

import com.paymentology.reconsilation_service.helper.MATCHING_RESULT;
import com.paymentology.reconsilation_service.helper.ReconciliationStrategy;
import com.paymentology.reconsilation_service.model.FileResponseMetaData;
import com.paymentology.reconsilation_service.model.ReconcileReportData;
import com.paymentology.reconsilation_service.model.Transaction;
import com.paymentology.reconsilation_service.util.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReconcileServiceImplTest {
    @Mock
    private ReconciliationStrategy reconciliationStrategy;

    @Mock
    private MultipartFile file1;

    @Mock
    private MultipartFile file2;

    @InjectMocks
    private ReconcileServiceImpl reconcileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReconciledetails() throws Exception {

        try (MockedStatic<CSVUtils> csvutilsMock = Mockito.mockStatic(CSVUtils.class)) {
            Transaction transaction1 = new Transaction("Profile1", LocalDateTime.now(), 100, "Narrative1", "Description1", "001", "Type1", "Wallet1");
            Transaction transaction2 = new Transaction("Profile2", LocalDateTime.now(), 200, "Narrative2", "Description2", "001", "Type2", "Wallet2");

            List<Transaction> file1Transactions = List.of(transaction1);
            List<Transaction> file2Transactions = List.of(transaction2);

            csvutilsMock.when(()->CSVUtils.convertToBean(file1)).thenReturn(file1Transactions);
            csvutilsMock.when(()->CSVUtils.convertToBean(file2)).thenReturn(file2Transactions);


            // Mocking file names
            when(file1.getOriginalFilename()).thenReturn("file1.csv");
            when(file2.getOriginalFilename()).thenReturn("file2.csv");

            // Mocking the reconciliation strategy
            when(reconciliationStrategy.getMatchingResult(transaction1, transaction2)).thenReturn(MATCHING_RESULT.NOT_MATCH);

            // Execute the service method
            ReconcileReportData reportData = reconcileService.getReconciledetails(file1, file2);

            // Verify results
            assertNotNull(reportData);
            assertEquals(2, reportData.getData().size());

            List<FileResponseMetaData> l = new ArrayList<>(reportData.getData());

            FileResponseMetaData file1MetaData = l.get(0);
            FileResponseMetaData file2MetaData = l.get(1);


            assertEquals("file1.csv", file1MetaData.getFileName());
            assertEquals(1, file1MetaData.getTotalRecords());
            assertEquals(0, file1MetaData.getTotalUnMatchedRecords());
            assertEquals(1, file1MetaData.getTotalMatchedRecords());

            assertEquals("file2.csv", file2MetaData.getFileName());
            assertEquals(1, file2MetaData.getTotalRecords());
            assertEquals(0, file2MetaData.getTotalUnMatchedRecords());
            assertEquals(1, file2MetaData.getTotalMatchedRecords());

            verify(reconciliationStrategy, times(1))
                    .getMatchingResult(transaction1, transaction2);
        }
    }

}