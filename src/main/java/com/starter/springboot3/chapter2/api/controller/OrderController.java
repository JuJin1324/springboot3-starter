package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.api.controller.dto.OrderRequestDto;
import com.starter.springboot3.chapter2.api.controller.dto.OrderResponseDto;
import com.starter.springboot3.chapter2.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDto>> createOrder(
            @RequestBody OrderRequestDto orderRequest) {
        return orderService
                .createOrder(orderRequest)
                .map(orderResponse -> ResponseEntity.status(HttpStatus.CREATED).body(orderResponse))
                .onErrorResume(
                        e ->
                                Mono.just(
                                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                .body(
                                                        new OrderResponseDto(
                                                                null,
                                                                null,
                                                                orderRequest.productName(),
                                                                orderRequest.quantity(),
                                                                "FAILED: " + e.getMessage()))));
    }
}
