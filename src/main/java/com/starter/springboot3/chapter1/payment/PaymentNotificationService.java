package com.starter.springboot3.chapter1.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentNotificationService {
    public String generateNotificationMessage(PaymentStatus status) {
        return switch (status) {
            case PaymentPending p ->
                    String.format("결제 대기 중 (거래 ID: %s): %s", p.transactionId(), p.message());
            case PaymentSuccess s ->
                    String.format(
                            "결제 성공! (거래 ID: %s, 승인번호: %s, 승인일시: %s)",
                            s.transactionId(), s.approvalCode(), s.approvedAt().toString());
            case PaymentFailed f ->
                    String.format(
                            "결제 실패 (거래 ID: %s, 에러코드: %s): %s - 실패일시: %s",
                            f.transactionId(),
                            f.errorCode(),
                            f.errorMessage(),
                            f.failedAt().toString());
            case PaymentCanceled c ->
                    String.format(
                            "결제 취소 (거래 ID: %s, 사유: %s, 취소일시: %s)",
                            c.transactionId(), c.reason(), c.canceledAt().toString());
                // 봉인 인터페이스의 모든 허용된 타입을 case로 다루면 default 불필요
        };
    }
}
