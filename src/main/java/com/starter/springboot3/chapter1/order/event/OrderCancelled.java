package com.starter.springboot3.chapter1.order.event;

import java.time.LocalDateTime;

public record OrderCancelled(String orderId, String reason, LocalDateTime occurredAt) implements OrderEvent {}
