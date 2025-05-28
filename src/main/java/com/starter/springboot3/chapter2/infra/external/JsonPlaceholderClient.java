package com.starter.springboot3.chapter2.infra.external;

import com.starter.springboot3.chapter2.service.Post;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
// src/main/java/com/example/httpinterface/client/JsonPlaceholderClient.java

// 인터페이스 레벨에 @HttpExchange를 사용하여 기본 URL 및 공통 설정 가능
@HttpExchange(url = "/posts", accept = "application/json", contentType = "application/json")
public interface JsonPlaceholderClient {
    @GetExchange
    List<Post> getAllPosts();

    @GetExchange("/{id}")
    Post getPostById(@PathVariable Long id);

    @PostExchange
    Post createPost(Post newPost); // @RequestBody Post newPost 와 동일하게 동작 가능
}

