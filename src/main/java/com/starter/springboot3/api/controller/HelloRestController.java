package com.starter.springboot3.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/22 */
@RestController
public class HelloRestController {
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        for (int i = 0; i < 100; i++) {
            Math.sqrt(i);
        }
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    public record Greeting(long id, String content) {}
}
