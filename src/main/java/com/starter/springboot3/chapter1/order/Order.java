package com.starter.springboot3.chapter1.order;

import java.util.List;

public class Order {
    private Long id;
    private Money totalPrice; // Money VO를 필드로 가짐
    private List<OrderLine> orderLines;

    public Order(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
        this.totalPrice = calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        Money sum = Money.of("0", "KRW"); // 기본 통화 가정
        for (OrderLine line : orderLines) {
            sum = sum.add(line.calculatePrice());
        }
        return sum;
    }
}
