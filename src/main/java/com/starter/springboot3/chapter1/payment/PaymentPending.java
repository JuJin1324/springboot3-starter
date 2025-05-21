package com.starter.springboot3.chapter1.payment;

// 허용된 구현체들 (레코드로 간결하게 각 상태의 추가 정보 담기)
public record PaymentPending(String transactionId, String message) implements PaymentStatus {}
