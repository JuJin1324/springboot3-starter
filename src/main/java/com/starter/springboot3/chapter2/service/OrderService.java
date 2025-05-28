package com.starter.springboot3.chapter2.service;

import com.starter.springboot3.chapter2.api.controller.dto.OrderRequestDto;
import com.starter.springboot3.chapter2.api.controller.dto.OrderResponseDto;
import com.starter.springboot3.chapter2.infra.external.UserServiceClient;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserServiceClient userServiceClient;
    private final AtomicLong orderIdGenerator = new AtomicLong(0); // 간단한 주문 ID 생성

    public Mono<OrderResponseDto> createOrder(OrderRequestDto orderRequest) {
        // 1. UserServiceClient를 통해 사용자 정보 조회
        return userServiceClient
                .getUserById(orderRequest.userId())
                .flatMap(
                        userDto -> {
                            // 사용자 정보가 성공적으로 조회되면 주문 생성 로직 진행
                            System.out.println(
                                    "Order Service: Received user info from User Service: "
                                            + userDto);
                            // 실제로는 여기서 주문 정보를 DB에 저장하는 등의 로직이 들어감
                            Long newOrderId = orderIdGenerator.incrementAndGet();
                            OrderResponseDto orderResponse =
                                    new OrderResponseDto(
                                            newOrderId,
                                            userDto, // 조회된 사용자 정보 포함
                                            orderRequest.productName(),
                                            orderRequest.quantity(),
                                            "PENDING" // 초기 주문 상태
                                            );
                            System.out.println("Order Service: Order created: " + orderResponse);
                            return Mono.just(orderResponse);
                        })
                .onErrorResume(
                        throwable -> {
                            // 사용자 정보 조회 실패 또는 다른 예외 발생 시 처리
                            System.err.println(
                                    "Order Service: Failed to get user info or create order: "
                                            + throwable.getMessage());
                            // 적절한 예외를 반환하거나, 기본값 또는 에러 응답 DTO를 반환할 수 있음
                            return Mono.error(
                                    new RuntimeException(
                                            "Failed to create order, user not found or user service error.",
                                            throwable));
                        });
    }
}
