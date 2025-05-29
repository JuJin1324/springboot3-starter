package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.api.controller.dto.CreateOrderRequest;
import com.starter.springboot3.chapter2.api.controller.exception.InsufficientStockException;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@RestController
@RequestMapping("/api/orders/exception")
public class OrderExceptionController {

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        // 간단한 재고 확인 로직 (실제로는 서비스 레이어에서 처리)
        if ("PROD-123".equals(request.productId()) && request.quantity() > 10) {
            throw new InsufficientStockException(request.productId(), request.quantity(), 10);
        }
        if ("PROD-SOLD-OUT".equals(request.productId())) {
            throw new InsufficientStockException(request.productId(), request.quantity(), 0);
        }

        // 성공 시
        return ResponseEntity.ok("Order created successfully for product: " + request.productId());
    }
}
