package com.paymentology.reconsilation_service.helper;

import com.paymentology.reconsilation_service.model.Transaction;

public interface ReconciliationStrategy {

    MATCHING_RESULT getMatchingResult(Transaction t1, Transaction t2);
}
