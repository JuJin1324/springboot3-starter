package com.starter.springboot3.chapter3;

import java.util.List;

/**
 * 페이지네이션을 위한 제네릭 래퍼 클래스
 * 
 * @param <T> 페이지네이션할 데이터 타입
 */
public record Paginated<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int currentPage,
    int size,
    boolean hasNext,
    boolean hasPrevious
) {
    
    /**
     * 빈 페이지 생성
     */
    public static <T> Paginated<T> empty() {
        return new Paginated<>(
            List.of(),
            0L,
            0,
            0,
            10,
            false,
            false
        );
    }
    
    /**
     * 페이지네이션 정보와 함께 데이터 생성
     */
    public static <T> Paginated<T> of(List<T> content, long totalElements, int currentPage, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean hasNext = currentPage < totalPages - 1;
        boolean hasPrevious = currentPage > 0;
        
        return new Paginated<>(
            content,
            totalElements,
            totalPages,
            currentPage,
            size,
            hasNext,
            hasPrevious
        );
    }
}
