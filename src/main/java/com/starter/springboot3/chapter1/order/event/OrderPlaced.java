package com.starter.springboot3.chapter1.order.event;

import com.starter.springboot3.chapter1.order.Money;

import java.time.LocalDateTime;

public record OrderPlaced(String orderId, String customerId, Money totalAmount, LocalDateTime occurredAt) implements OrderEvent {}
