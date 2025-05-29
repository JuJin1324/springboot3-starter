package com.starter.springboot3.chapter2.config;

import com.starter.springboot3.chapter2.infra.external.JsonPlaceholderClient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate jsonPlaceholderRestTemplate() {
        return new RestTemplateBuilder().rootUri("http://localhost:8080").build();
    }

    @Bean
    public JsonPlaceholderClient jsonPlaceholderClientWithRestTemplate(
            RestTemplate jsonPlaceholderRestTemplate) {

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builder()
                        .exchangeAdapter(RestTemplateAdapter.create(jsonPlaceholderRestTemplate))
                        .build();
        return factory.createClient(JsonPlaceholderClient.class);
    }
}
