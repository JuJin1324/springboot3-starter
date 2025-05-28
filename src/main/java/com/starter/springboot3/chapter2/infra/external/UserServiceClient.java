package com.starter.springboot3.chapter2.infra.external;

import com.starter.springboot3.chapter2.service.UserDto;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import reactor.core.publisher.Mono;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@HttpExchange("/api/users") // 기본 경로 접두사 (선택 사항)
public interface UserServiceClient {

    // GET /users/{userId} : 특정 사용자 정보 조회
    @GetExchange("/{userId}")
    Mono<UserDto> getUserById(@PathVariable Long userId);
}
