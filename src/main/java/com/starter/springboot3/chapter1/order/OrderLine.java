package com.starter.springboot3.chapter1.order;

import java.math.BigDecimal;

public record OrderLine(String productId, Money unitPrice, int quantity) {
    public OrderLine {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    public Money calculatePrice() {
        return new Money(
                unitPrice.amount().multiply(BigDecimal.valueOf(quantity)), unitPrice.currency());
    }
}
