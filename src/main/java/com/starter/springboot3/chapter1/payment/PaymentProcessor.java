package com.starter.springboot3.chapter1.payment;

import com.starter.springboot3.chapter1.order.Money;

import java.time.LocalDateTime;

public class PaymentProcessor {
    public PaymentStatus process(String orderId, Money amount) {
        // ... 결제 처리 로직 ...
        if (/* 성공 조건 */ true) { // 실제 조건으로 대체
            return new PaymentSuccess("TX123", "APPROVAL_XYZ", LocalDateTime.now());
        } else if (/* 실패 조건 */ false) { // 실제 조건으로 대체
            return new PaymentFailed("TX456", "E001", "잔액 부족", LocalDateTime.now());
        }
        // ... 기타 상태 ...
        return new PaymentPending("TX789", "PG사 응답 대기중");
    }
}
