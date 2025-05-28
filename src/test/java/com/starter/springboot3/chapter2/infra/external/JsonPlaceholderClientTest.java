package com.starter.springboot3.chapter2.infra.external;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.starter.springboot3.chapter2.service.Post;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JsonPlaceholderClientTest {
    @Autowired
    private JsonPlaceholderClient client;

    @Test
    void getAllPosts() {
        var allPosts = client.getAllPosts();
        System.out.println("allPosts = " + allPosts);
    }
}
