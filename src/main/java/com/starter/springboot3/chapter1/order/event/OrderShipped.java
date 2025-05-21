package com.starter.springboot3.chapter1.order.event;

import java.time.LocalDateTime;

public record OrderShipped(String orderId, String trackingNumber, LocalDateTime occurredAt) implements OrderEvent {}
