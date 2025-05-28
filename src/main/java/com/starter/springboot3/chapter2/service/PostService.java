package com.starter.springboot3.chapter2.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/28 */
@Service
public class PostService {
    private static Map<Long, Post> posts = new ConcurrentHashMap<>();

    public PostService() {
        posts.put(1L, new Post(1L, 1L, "제목1", "내용1"));
        posts.put(2L, new Post(2L, 1L, "제목2", "내용2"));
        posts.put(3L, new Post(3L, 1L, "제목3", "내용3"));
        posts.put(4L, new Post(4L, 1L, "제목4", "내용4"));
        posts.put(5L, new Post(5L, 1L, "제목5", "내용5"));
        posts.put(6L, new Post(6L, 1L, "제목6", "내용6"));
    }

    public List<Post> fetchAllPosts() {
        return posts.values().stream().toList();
    }

    public Post fetchPostById(Long id) {
        return posts.get(id);
    }

    public Post publishNewPost(Post post) {
        return posts.put(post.id(), post);
    }
}
