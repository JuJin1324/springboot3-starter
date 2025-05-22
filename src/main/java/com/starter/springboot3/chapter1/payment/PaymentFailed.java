package com.starter.springboot3.chapter1.payment;

import java.time.LocalDateTime;

public record PaymentFailed(
        String transactionId, String errorCode, String errorMessage, LocalDateTime failedAt)
        implements PaymentStatus {}
