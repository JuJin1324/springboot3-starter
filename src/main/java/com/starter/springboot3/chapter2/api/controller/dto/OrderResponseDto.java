package com.starter.springboot3.chapter2.api.controller.dto;

import com.starter.springboot3.chapter2.service.UserDto;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
public record OrderResponseDto(
	Long orderId, UserDto user, String productName, int quantity, String status) {}
