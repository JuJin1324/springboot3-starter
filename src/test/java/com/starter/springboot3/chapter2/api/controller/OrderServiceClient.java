package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.api.controller.dto.OrderRequestDto;
import com.starter.springboot3.chapter2.api.controller.dto.OrderResponseDto;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import reactor.core.publisher.Mono;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
public interface OrderServiceClient {

    @PostExchange("/api/orders")
    Mono<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest);
}
