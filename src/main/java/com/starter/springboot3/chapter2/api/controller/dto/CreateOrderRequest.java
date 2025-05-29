package com.starter.springboot3.chapter2.api.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotBlank(message = "상품 ID는 필수입니다.") String productId,
        @NotNull(message = "수량은 필수입니다.") @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
                Integer quantity) {}
