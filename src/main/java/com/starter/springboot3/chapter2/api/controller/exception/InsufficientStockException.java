package com.starter.springboot3.chapter2.api.controller.exception;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {
    private final String productId;
    private final int requestedQuantity;
    private final int availableStock;

    public InsufficientStockException(String productId, int requestedQuantity, int availableStock) {
        super(
                String.format(
                        "Product ID '%s' has insufficient stock. Requested: %d, Available: %d",
                        productId, requestedQuantity, availableStock));
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableStock = availableStock;
    }
}
