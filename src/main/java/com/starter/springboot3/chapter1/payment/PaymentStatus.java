package com.starter.springboot3.chapter1.payment;

public sealed interface PaymentStatus permits PaymentPending, PaymentSuccess, PaymentFailed, PaymentCanceled {
    String transactionId();
}

