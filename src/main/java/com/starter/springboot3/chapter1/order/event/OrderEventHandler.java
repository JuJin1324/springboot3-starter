package com.starter.springboot3.chapter1.order.event;

import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    // @EventListener // Spring 환경이라면
    public void handleOrderEvent(OrderEvent event) {
        System.out.println("Received event for order: " + event.orderId() + " at " + event.occurredAt());

        // Java 21+ (or Java 17 with --enable-preview)
        switch (event) {
            case OrderPlaced op -> handleOrderPlaced(op);
            case OrderShipped os -> handleOrderShipped(os);
            case OrderItemAdded oia -> handleOrderItemAdded(oia);
            case OrderCancelled oc -> handleOrderCancelled(oc);
            // default는 필요 없음 (모든 타입 처리 시)
        }
    }

    private void handleOrderPlaced(OrderPlaced event) {
        System.out.println("Order Placed: Customer " + event.customerId() + ", Amount: " + event.totalAmount());
        // ... 주문 생성 후처리 로직 (예: 알림 발송, 재고 감소 요청 등) ...
    }

    private void handleOrderShipped(OrderShipped event) {
        System.out.println("Order Shipped: Tracking " + event.trackingNumber());
        // ... 배송 시작 알림 ...
    }

    private void handleOrderItemAdded(OrderItemAdded event) {
        System.out.println("Item Added: Product " + event.productId() + ", Quantity: " + event.quantity());
        // ... 주문 금액 재계산, 재고 확인 등 ...
    }

    private void handleOrderCancelled(OrderCancelled event) {
        System.out.println("Order Cancelled: Reason " + event.reason());
        // ... 환불 처리, 재고 복구 등 ...
    }
}

