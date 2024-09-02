package com.paymentology.reconsilation_service.helper;

import com.paymentology.reconsilation_service.model.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "reconcile.strategy", matchIfMissing = true, havingValue = "priority")
public class PriorityBasedReconciliationStrategyImpl implements ReconciliationStrategy {

    private final int exactMatchValue = 33;
    private final int closeMatchThresholdValue;

    public PriorityBasedReconciliationStrategyImpl() {
        this.closeMatchThresholdValue = 19;
    }

    @Override
    public MATCHING_RESULT getMatchingResult(Transaction t1, Transaction t2) {
        int result = getPriorityScore(t1, t2);

        if (result == exactMatchValue) {
            return MATCHING_RESULT.MATCH;
        } else if (result >= closeMatchThresholdValue) {
            return MATCHING_RESULT.CLOSE_MATCH;
        } else {
            return MATCHING_RESULT.NOT_MATCH;
        }
    }

    private int getPriorityScore(Transaction t1, Transaction t2) {
        int compareTotal = 0;
        if (t1 == null || t2 == null) {
            return compareTotal;
        }

        if (StringUtils.equals(t1.getId(), t2.getId())) {
            compareTotal += FIELD_PRIORITY.TRANSACTION_ID.getPriority();
        }

        if (StringUtils.equals(t1.getWalletReference(), t2.getWalletReference())) {
            compareTotal += FIELD_PRIORITY.WALLET_REFERENCE.getPriority();
        }

        if (StringUtils.equals(t1.getType(), t2.getType())) {
            compareTotal += FIELD_PRIORITY.TYPE.getPriority();
        }

        if (t1.getAmount() == t2.getAmount()) {
            compareTotal += FIELD_PRIORITY.AMOUNT.getPriority();
        }

        if (t1.getDate().equals(t2.getDate())) {
            compareTotal += FIELD_PRIORITY.DATE.getPriority();
        }

        if (StringUtils.equalsIgnoreCase(t1.getDescription(), t2.getDescription())) {
            compareTotal += FIELD_PRIORITY.DESCRIPTION.getPriority();
        }

        if (StringUtils.equals(t1.getNarrative(), t2.getNarrative())) {
            compareTotal += FIELD_PRIORITY.NARRATIVE.getPriority();
        }

        return compareTotal;
    }
}
