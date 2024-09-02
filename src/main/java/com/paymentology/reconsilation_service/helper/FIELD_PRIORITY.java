package com.paymentology.reconsilation_service.helper;

public enum FIELD_PRIORITY {
    TRANSACTION_ID(7),
    WALLET_REFERENCE(7),
    TYPE(5),
    AMOUNT(5),
    DATE(5),
    DESCRIPTION(3),
    NARRATIVE(1);
    final int priority;

    FIELD_PRIORITY(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
