package com.starter.springboot3.chapter3;

/**
 * User DTO 클래스
 * Spring Boot 3.x에서 Record 활용 (Java 17+)
 */
public record UserDto(
    Long id,
    String name,
    String email,
    String department
) {
    
    // 정적 팩토리 메서드
    public static UserDto of(Long id, String name, String email, String department) {
        return new UserDto(id, name, email, department);
    }
}
