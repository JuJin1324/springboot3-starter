# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 빌드 및 개발 명령어

### 빌드 시스템
- **주요 빌드**: `./gradlew build` (Java 21 기반 Gradle)
- **레거시 빌드**: `mvn clean install` (Java 8 기반 esim 모듈용 Maven)
- **애플리케이션 실행**: `./gradlew bootRun`
- **네이티브 이미지**: `./gradlew nativeCompile` (GraalVM 네이티브 컴파일)
- **테스트 실행**: `./gradlew test`
- **단일 테스트**: `./gradlew test --tests ClassName.methodName`
- **특정 패키지 테스트**: `./gradlew test --tests com.starter.springboot3.chapter2.*`

### 가상 스레드
Java 21 가상 스레드는 application.yml의 `spring.threads.virtual.enabled: true` 설정으로 기본 활성화됨.

## 프로젝트 아키텍처

### 이중 애플리케이션 구조
- **메인 애플리케이션**: `com.starter.springboot3.Springboot3StarterApplication` (Spring Boot 3.4.5, Java 21)
- **레거시 모듈**: `com.example.esim.EsimScpManagementApplication` (Spring Boot 2.7.18, Java 8)

### 챕터 기반 학습 구조
- **Chapter 1**: Java 21 기능을 활용한 도메인 모델링 (sealed interfaces, pattern matching, records)
  - 이벤트 기반 아키텍처를 가진 Order 도메인
  - Sealed hierarchy를 이용한 Payment 처리
  - 통화 연산을 포함한 Money 값 객체
- **Chapter 2**: 반응형 패턴과 최신 Spring 기능을 가진 API 계층
  - RFC 7807 준수 에러 처리
  - `@HttpExchange`를 사용한 선언적 HTTP 클라이언트
  - Micrometer Observation API 통합
- **Chapter 3**: GraalVM Native Image와 성능 최적화
  - 네이티브 이미지 메모리 및 시작 시간 모니터링
  - 제네릭 타입과 리플렉션 제한 사항 처리
  - `@RegisterReflectionForBinding`을 활용한 최적화된 설정
  - 동적 프록시와 리소스 접근 패턴

### 주요 아키텍처 패턴
- **도메인 이벤트**: Switch 표현식에서 패턴 매칭을 사용한 Sealed interface 계층구조
- **반응형 프로그래밍**: 비블로킹 연산을 위해 `Mono<ResponseEntity<T>>`를 반환하는 컨트롤러
- **값 객체**: 불변 레코드 및 클래스 (예: `Money`)
- **외부 서비스 통합**: 마이크로서비스 통신을 위한 `@HttpExchange` 인터페이스
- **관찰가능성**: High/low cardinality 태그를 가진 내장 Micrometer Observation API
- **네이티브 최적화**: 최소한의 리플렉션 설정과 제네릭 타입 안전성
- **성능 모니터링**: 메모리 사용량, 시작 시간, GC 정보 추적

### 사용된 최신 Java 21 기능
- 타입 안전 계층구조를 위한 Sealed interfaces/classes
- Switch 표현식에서의 패턴 매칭
- 불변 DTO 및 값 객체를 위한 Records
- 향상된 동시성을 위한 가상 스레드

### 설정
- **Actuator 엔드포인트**: Health, metrics, prometheus, observations 활성화
- **HTTP 클라이언트**: `HttpServiceProxyFactory` 빈을 통한 설정
- **에러 처리**: RFC 7807 Problem Details를 구현하는 전역 예외 핸들러

### 테스트 패턴
- 정의된 포트를 사용하는 `@SpringBootTest` 통합 테스트
- 실제 서비스 프록시 클라이언트를 이용한 HTTP 클라이언트 테스트
- 테스트에서의 반응형 WebClient 패턴

## 개발 워크플로우

### 새로운 기능 개발 시
1. **도메인 모델 우선**: Chapter 1의 패턴을 따라 sealed interfaces와 records 활용
2. **이벤트 기반 설계**: OrderEvent 계층구조처럼 도메인 이벤트 정의
3. **반응형 API**: Chapter 2의 패턴을 따라 `Mono<ResponseEntity<T>>` 반환
4. **에러 처리**: GlobalExceptionHandler와 RFC 7807 Problem Details 활용
5. **관찰가능성**: @Observed 어노테이션 또는 Observation API 직접 사용

### 코드 스타일 가이드
- **Java 21 기능 적극 활용**: Pattern matching, sealed types, records
- **불변성 우선**: Records와 불변 클래스 선호
- **명시적 타입 안전성**: Sealed hierarchies로 컴파일 타임 보장
- **반응형 패턴**: WebFlux 스타일의 비블로킹 연산

### 핵심 패턴 예시

**도메인 이벤트 처리**:
```java
// OrderEventHandler.java:105-115 참조
public void handle(OrderEvent event) {
    switch (event) {
        case OrderPlaced(var orderId, var customerId, var items) -> 
            // 주문 처리 로직
        case OrderCancelled(var orderId, var reason) -> 
            // 취소 처리 로직
    }
}
```

**HTTP 클라이언트 패턴**:
```java
// JsonPlaceholderClient.java 참조
@HttpExchange("/posts")
public interface JsonPlaceholderClient {
    @GetExchange("/{id}")
    Mono<Post> getPost(@PathVariable Long id);
}
```

**에러 응답 패턴**:
```java
// GlobalExceptionHandler.java 참조
@ExceptionHandler(InsufficientStockException.class)
public ResponseEntity<ProblemDetail> handleInsufficientStock(
    InsufficientStockException ex) {
    // RFC 7807 준수 에러 응답
}
```

**Native Image 최적화 패턴**:
```java
// ProblematicController.java 참조
@RegisterReflectionForBinding({
    ProblematicController.TestObject.class
})
public class ProblematicController {
    // 최소한의 리플렉션 설정으로 JSON 직렬화 지원
}
```

**제네릭 타입 안전성**:
```java
// GenericUserController.java 참조
public Paginated<UserDto> getUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int size) {
    // ✅ 타입 안전한 제네릭 반환
}
```