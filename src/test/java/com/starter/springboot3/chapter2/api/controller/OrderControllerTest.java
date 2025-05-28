package com.starter.springboot3.chapter2.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.starter.springboot3.chapter2.api.controller.dto.OrderRequestDto;
import com.starter.springboot3.chapter2.api.controller.dto.OrderResponseDto;

import reactor.core.publisher.Mono;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderControllerTest {
    private OrderServiceClient client;

    @BeforeEach
    void setUp() {
        WebClient webClient =
                WebClient.builder()
                        .baseUrl("http://localhost:8080") // 사용자 서비스의 기본 URL 설정
                        .build();
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builder()
                        .exchangeAdapter(WebClientAdapter.create(webClient))
                        .build();
        client = factory.createClient(OrderServiceClient.class);
    }

    @Test
    void createOrder() {
        var laptopOrder = client.createOrder(new OrderRequestDto(1L, "Laptop", 1))
            .block();
        System.out.println("laptopOrder = " + laptopOrder);
    }
}
