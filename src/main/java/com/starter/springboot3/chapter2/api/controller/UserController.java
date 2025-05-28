package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.service.UserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Map<Long, UserDto> users = new ConcurrentHashMap<>();

    public UserController() {
        users.put(1L, new UserDto(1L, "john_doe"));
        users.put(2L, new UserDto(2L, "jane_smith"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto user = users.get(userId);
        if (user != null) {
            System.out.println("User Service: User " + userId + " requested. Sending: " + user);
            return ResponseEntity.ok(user);
        } else {
            System.out.println("User Service: User " + userId + " not found.");
            return ResponseEntity.notFound().build();
        }
    }
}
