package com.starter.springboot3.chapter2.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.starter.springboot3.chapter2.api.controller.dto.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderExceptionControllerTest {
    private OrderServiceClient client;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate =
                new RestTemplateBuilder().rootUri("http://localhost:8080").build();

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builder()
                        .exchangeAdapter(RestTemplateAdapter.create(restTemplate))
                        .build();
        client = factory.createClient(OrderServiceClient.class);
    }

    @Test
    void createOrder_normal() {
        CreateOrderRequest request = new CreateOrderRequest("PROD-001", 5);
        try{
            String orderWithException = client.createOrderWithException(request);
            System.out.println("orderWithException = " + orderWithException);
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    @Test
    void createOrder_abnormal_business() {
        CreateOrderRequest request = new CreateOrderRequest("PROD-123", 15);
        try{
            String orderWithException = client.createOrderWithException(request);
            System.out.println("orderWithException = " + orderWithException);
        } catch (HttpClientErrorException e) {
            System.out.println("e = " + e);
        }
    }

    @Test
    void createOrder_abnormal_param() {
        CreateOrderRequest request = new CreateOrderRequest("", 0);
        try{
            String orderWithException = client.createOrderWithException(request);
            System.out.println("orderWithException = " + orderWithException);
        } catch (HttpClientErrorException e) {
            System.out.println("e = " + e);
        }
    }
}
