package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.infra.external.UserServiceClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@Configuration
public class AppClientConfig {

    // application.properties에서 사용자 서비스 URL 주입
    private String userServiceBaseUrl = "http://localhost:8080";

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(userServiceBaseUrl) // 사용자 서비스의 기본 URL 설정
                // .defaultHeader(...) // 필요한 공통 헤더 설정
                // .filter(...) // 공통 로깅, 에러 처리 필터 등 추가 가능
                .build();
    }

    @Bean
    public UserServiceClient userServiceClient(WebClient userWebClient) {
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builder()
                        .exchangeAdapter(WebClientAdapter.create(userWebClient))
                        .build();
        return factory.createClient(UserServiceClient.class);
    }
}
