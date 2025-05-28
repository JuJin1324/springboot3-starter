package com.starter.springboot3.chapter2.api.controller.dto;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
public record OrderRequestDto(Long userId, String productName, int quantity) {}
