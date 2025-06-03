package com.starter.springboot3.chapter3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 제네릭 타입을 사용하는 RestController 예시
 * 
 * Spring Boot 3.x Native Image에서 제네릭 타입 처리 방법을 보여줍니다.
 */
@RestController
@RequestMapping("/api/v2/users")
public class GenericUserController {
    
    // 샘플 데이터
    private static final List<UserDto> SAMPLE_USERS = List.of(
        UserDto.of(1L, "김철수", "kim@example.com", "개발팀"),
        UserDto.of(2L, "이영희", "lee@example.com", "마케팅팀"),
        UserDto.of(3L, "박민수", "park@example.com", "디자인팀"),
        UserDto.of(4L, "최지영", "choi@example.com", "개발팀"),
        UserDto.of(5L, "정우진", "jung@example.com", "기획팀")
    );
    
    /**
     * 페이지네이션된 사용자 목록 조회
     * 
     * ✅ 이 경우 Paginated<UserDto>는 자동으로 reflection 정보가 보존됨
     * Spring Boot가 반환 타입을 분석하여 처리
     */
    @GetMapping
    public Paginated<UserDto> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        
        int start = page * size;
        int end = Math.min(start + size, SAMPLE_USERS.size());
        
        if (start >= SAMPLE_USERS.size()) {
            return Paginated.empty();
        }
        
        List<UserDto> pageContent = SAMPLE_USERS.subList(start, end);
        return Paginated.of(pageContent, SAMPLE_USERS.size(), page, size);
    }
    
    /**
     * 단일 사용자 조회
     * 
     * ✅ ResponseEntity<UserDto>도 자동 처리됨
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<UserDto> user = SAMPLE_USERS.stream()
                .filter(u -> u.id().equals(id))
                .findFirst();
        
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 복잡한 제네릭 타입 - Map 기반
     * 
     * ⚠️ 이런 경우는 Native Image에서 문제가 될 수 있음
     * Map<String, Object>의 Object 타입이 런타임에 결정됨
     */
    @GetMapping("/stats")
    public Paginated<Map<String, Object>> getUserStats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        
        List<Map<String, Object>> stats = SAMPLE_USERS.stream()
                .map(user -> Map.<String, Object>of(
                    "id", user.id(),
                    "name", user.name(),
                    "department", user.department(),
                    "emailDomain", user.email().split("@")[1],
                    "nameLength", user.name().length()
                ))
                .toList();
        
        int start = page * size;
        int end = Math.min(start + size, stats.size());
        
        if (start >= stats.size()) {
            return Paginated.empty();
        }
        
        List<Map<String, Object>> pageContent = stats.subList(start, end);
        return Paginated.of(pageContent, stats.size(), page, size);
    }
    
    /**
     * 동적 타입 반환 - 가장 문제가 되는 케이스
     * 
     * ❌ Object 타입으로 반환하면 Native Image에서 문제 발생 가능
     */
    @GetMapping("/dynamic/{type}")
    public Object getDynamicData(@PathVariable String type) {
        return switch (type.toLowerCase()) {
            case "users" -> Paginated.of(SAMPLE_USERS, SAMPLE_USERS.size(), 0, 10);
            case "single" -> SAMPLE_USERS.get(0);
            case "count" -> Map.of("total", SAMPLE_USERS.size());
            default -> Map.of("error", "Unknown type: " + type);
        };
    }
    
    /**
     * 중첩된 제네릭 타입
     * 
     * ⚠️ List<Paginated<UserDto>>와 같은 중첩 제네릭도 주의 필요
     */
    @GetMapping("/departments")
    public List<Paginated<UserDto>> getUsersByDepartments() {
        Map<String, List<UserDto>> grouped = SAMPLE_USERS.stream()
                .collect(java.util.stream.Collectors.groupingBy(UserDto::department));
        
        return grouped.values().stream()
                .map(users -> Paginated.of(users, users.size(), 0, users.size()))
                .toList();
    }
}
