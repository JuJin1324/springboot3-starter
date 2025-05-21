package com.starter.springboot3.chapter1.order.event;

import java.time.LocalDateTime;

public sealed interface OrderEvent permits OrderPlaced, OrderShipped, OrderItemAdded, OrderCancelled {
    String orderId();
    LocalDateTime occurredAt();
}
