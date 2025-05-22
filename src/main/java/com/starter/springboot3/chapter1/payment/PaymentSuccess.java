package com.starter.springboot3.chapter1.payment;

import java.time.LocalDateTime;

public record PaymentSuccess(String transactionId, String approvalCode, LocalDateTime approvedAt)
        implements PaymentStatus {}
