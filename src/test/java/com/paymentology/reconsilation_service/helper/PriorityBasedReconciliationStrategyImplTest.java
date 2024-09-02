package com.paymentology.reconsilation_service.helper;

import com.paymentology.reconsilation_service.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriorityBasedReconciliationStrategyImplTest {

    PriorityBasedReconciliationStrategyImpl strategy = new PriorityBasedReconciliationStrategyImpl();

    @Test
    void getMatchingResultWithMatching() {
        LocalDateTime dateTime = LocalDateTime.now();
        Transaction t1 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P1")
                .description("DEBIT")
                .narrative("N-001")
                .date(dateTime)
                .amount(100)
                .build();

        Transaction t2 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P1")
                .description("DEBIT")
                .narrative("N-001")
                .date(dateTime)
                .amount(100)
                .build();

        MATCHING_RESULT result = strategy.getMatchingResult(t1,t2);

        assertNotNull(result);
        assertEquals(MATCHING_RESULT.MATCH,result);
    }

    @Test
    void getMatchingResultWithClosedMatch() {
        LocalDateTime dateTime = LocalDateTime.now();
        Transaction t1 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P1")
                .description("DEBIT")
                .narrative("N-001")
                .date(dateTime)
                .amount(100)
                .build();

        Transaction t2 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P2")
                .description("DEBIT FOR PURCHASE")
                .narrative("N-002")
                .date(dateTime)
                .amount(100)
                .build();

        MATCHING_RESULT result = strategy.getMatchingResult(t1,t2);

        assertNotNull(result);
        assertEquals(MATCHING_RESULT.CLOSE_MATCH,result);
    }

    @Test
    void getMatchingResultWithNotMatch() {
        LocalDateTime dateTime = LocalDateTime.now();
        Transaction t1 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P1")
                .description("DEBIT")
                .narrative("N-001")
                .date(dateTime)
                .amount(100)
                .build();

        Transaction t2 = Transaction.builder()
                .id("002")
                .walletReference("wallet-002")
                .type("2")
                .profileName("P2")
                .description("DEBIT FOR PURCHASE")
                .narrative("N-002")
                .date(dateTime)
                .amount(100)
                .build();

        MATCHING_RESULT result = strategy.getMatchingResult(t1,t2);

        assertNotNull(result);
        assertEquals(MATCHING_RESULT.NOT_MATCH,result);
    }


    @Test
    void getMatchingResultWithAnyTransactionNull() {
        LocalDateTime dateTime = LocalDateTime.now();
        Transaction t1 = Transaction.builder()
                .id("001")
                .walletReference("wallet-001")
                .type("1")
                .profileName("P1")
                .description("DEBIT")
                .narrative("N-001")
                .date(dateTime)
                .amount(100)
                .build();

        Transaction t2 = Transaction.builder()
                .id("002")
                .walletReference("wallet-002")
                .type("2")
                .profileName("P2")
                .description("DEBIT FOR PURCHASE")
                .narrative("N-002")
                .date(dateTime)
                .amount(100)
                .build();

        MATCHING_RESULT result = strategy.getMatchingResult(t1,null);

        assertNotNull(result);
        assertEquals(MATCHING_RESULT.NOT_MATCH,result);

        result = strategy.getMatchingResult(null,t2);

        assertNotNull(result);
        assertEquals(MATCHING_RESULT.NOT_MATCH,result);
    }
}