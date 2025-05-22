package com.starter.springboot3.chapter1.payment;

import java.time.LocalDateTime;

public record PaymentCanceled(String transactionId, String reason, LocalDateTime canceledAt) implements PaymentStatus {
    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
