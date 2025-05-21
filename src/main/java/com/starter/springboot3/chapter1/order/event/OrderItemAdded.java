package com.starter.springboot3.chapter1.order.event;

import com.starter.springboot3.chapter1.order.Money;

import java.time.LocalDateTime;

public record OrderItemAdded(String orderId, String productId, int quantity, Money itemPrice, LocalDateTime occurredAt) implements OrderEvent {}
