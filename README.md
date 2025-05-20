# SpringBoot3 Starter

## 1단계: 기반 다지기 - Java 17 & Jakarta EE 마이그레이션

### Java 17 필수

> * 왜 Java 17이 최소 요구 버전이 되었는지, Java 17의 주요 특징(Records, Sealed Classes, 향상된 switch 등)이 스프링 부트 3 개발에 어떻게
    영향을 줄 수 있는지 가볍게 살펴보기. (깊게 팔 필요는 없지만, 기본은 알아야 해!)
> * Java 17의 Records (불변 데이터 객체 간결화), Sealed Classes/Interfaces (상속/구현 제한), Pattern Matching for instanceof (타입 확인 및 캐스팅
    간소화) 등 주요 문법 학습 및 활용 연습
    이러한 기능들이 DDD의 Value Object 구현이나 다양한 패턴 매칭 시나리오에서 어떻게 활용될 수 있는지 고민해 보세요.

### Jakarta EE 9+ 전환 (`javax` -> `jakarta`)

> * 이 변경이 왜 일어났는지 배경을 이해하고, 실제 코드에서 임포트문 변경이 어떤 식으로 이루어지는지 파악. (가장 직접적인 코드 변경 사항!)
> * javax.* 네임스페이스에서 jakarta.* 네임스페이스로의 변경 배경 및 영향 범위 파악 (JPA, Servlet API, Bean Validation 등)
    기존 스프링 부트 2.x 프로젝트를 3.x로 마이그레이션 시 필요한 의존성 및 코드 변경 사항 학습 (특히 @Entity, @Table, @WebServlet, @NotNull 등의 어노테이션 변화)
    Spring Boot Migrator 도구 활용 가능성 탐색

---

## 2단계: Spring Framework 6 핵심 기능 마스터

### AOT (Ahead-Of-Time) 컴파일 및 네이티브 이미지 준비:

> * AOT 처리의 개념, 장점(시작 시간 단축, 메모리 사용량 감소), 단점(빌드 시간 증가, 리플렉션 제약) 이해
> * Spring AOT 엔진이 애플리케이션 컨텍스트를 분석하고 최적화하는 방식 학습
> * @NativeHint 등 네이티브 이미지 빌드를 위한 힌트 제공 방법 (3단계에서 GraalVM과 연계하여 심화 학습)

### HTTP Interface (선언적 HTTP 클라이언트):

> * Java 인터페이스 기반으로 HTTP 클라이언트를 선언적으로 작성하는 방법 (@GetExchange, @PostExchange 등)
> * 기존 RestTemplate이나 WebClient와 비교하여 장단점 분석 (가독성, 테스트 용이성)
> * MSA 환경에서 다른 서비스와의 통신 시 활용 방안 모색

### Problem Details for HTTP APIs (RFC 7807) 지원:

> * HTTP API 에러 응답을 표준화하는 RFC 7807 스펙 이해
> * Spring Boot 3에서 ProblemDetail 객체를 사용하여 에러 응답을 구성하고 반환하는 방법
> * @ControllerAdvice와 결합하여 전역 예외 처리 시 일관된 에러 포맷 제공 방법

### Micrometer Observation API 통합:

> * Micrometer 1.10부터 도입된 Observation API의 개념 (단일 API로 Metrics, Tracing, Logging 연관 데이터 수집)
> * ObservationRegistry, ObservationHandler 등을 활용한 Observability 구현
> * Spring Boot 3에서 자동 설정되는 부분과 커스텀 설정 방법 학습
> * Zipkin, Prometheus 등과 연동하여 실제 관측 데이터 확인 (3단계에서 심화)

--- 

## 3단계: 성능 최적화 및 차세대 기술 탐구

### GraalVM 네이티브 이미지 빌드 및 실행 (AOT 심화)

> * GraalVM 및 네이티브 이미지 개념, 장점(매우 빠른 시작 속도, 적은 메모리 사용량), 한계점(리플렉션, 동적 클래스 로딩 등 제한, 긴 빌드 시간) 심층 이해
> * Spring Boot 애플리케이션을 네이티브 이미지로 빌드하는 과정 (Buildpacks, Maven/Gradle 플러그인 활용)
> * 리플렉션, 리소스, 직렬화 등 네이티브 이미지 빌드 시 발생하는 문제 해결 및 @NativeHint 활용 전략
> * 네이티브 이미지 환경에서의 테스트 전략

### Observability 심화 (Micrometer Tracing & Metrics):

> * Micrometer Tracing (구 Spring Cloud Sleuth)을 활용한 분산 추적 시스템 구축 (Trace ID, Span ID 전파)
> * Zipkin, Jaeger 등 트레이싱 백엔드 연동 및 대시보드 활용
> * Micrometer Metrics를 활용한 다양한 애플리케이션 지표 수집 및 시각화 (Prometheus, Grafana 연동)
> * 커스텀 메트릭 정의 및 Observation API와의 연계 활용

### Project Loom과 가상 스레드 (Virtual Threads) - (Java 19+ 및 향후 전망):

> * Project Loom과 가상 스레드의 개념, 전통적인 플랫폼 스레드와의 차이점, 동시성 처리 모델의 변화 이해
> * Spring Framework 6.1 (또는 그 이후 버전) 및 Spring Boot에서의 가상 스레드 지원 현황 및 로드맵 파악 (Tomcat, Jetty 등 내장 웹 서버의 가상 스레드 지원 여부)
> * I/O 바운드 작업이 많은 애플리케이션에서의 성능 향상 가능성 탐색
> * ThreadLocal 사용 시 주의사항 등 가상 스레드 환경에서의 프로그래밍 모델 변화

---

## 4단계: 보안, 데이터, 기타 주요 업데이트 및 실전 적용

### Spring Security 6 주요 변경 사항

> * Lambda DSL을 사용한 보안 설정 간소화 및 가독성 향상
> * SecurityFilterChain 빈 등록 방식의 표준화 (WebSecurityConfigurerAdapter Deprecated)
> * 인가(Authorization) API 개선 (AuthorizationManager 등)
> * OAuth 2.0, OpenID Connect 지원 강화

### Spring Data JPA 및 기타 데이터 기술 변화

> * Spring Data JPA 2022.0 (코드명 Turing) 이후의 주요 변경 사항 (해당되는 경우)
> * Java Records를 JPA Entity나 DTO로 활용 시 고려 사항
> * R2DBC 등 반응형 데이터 접근 기술과의 연계 (필요시)

### 로깅 프레임워크 (Logback, Log4j2) 및 테스트 개선

> * Spring Boot 3에서의 로깅 설정 기본값 및 커스터마이징 방법
> * Testcontainers 지원 강화 및 통합 테스트 작성 용이성 증대
> * @SpringBootTest 어노테이션의 향상된 기능

### DDD 관점에서의 Spring Boot 3 활용

> * Java Records를 Value Object로 적극 활용하여 불변성과 명확성 증대
> * HTTP Interface를 활용한 Bounded Context 간 Anti-Corruption Layer (ACL) 구현
> * AOT 및 네이티브 이미지를 통해 도메인 모델의 복잡성이 성능에 미치는 영향 최소화 방안 모색
> * Observation API를 활용하여 도메인 이벤트 추적 및 모니터링 강화
> * 헥사고날 아키텍처에서 포트와 어댑터를 Spring 컴포넌트로 구성 시, Spring Boot 3의 유연성 활용

---

## 마이그레이션 전략 및 도구 활용

### 공식 마이그레이션 가이드 숙지

> Spring Boot 3 Migration Guide는 반드시 정독!

### Spring Boot Migrator 활용

> 자동 마이그레이션을 도와주는 도구 사용법 익히기.

---

## Java 21 가상 스레드 적용

> Java 21의 가상 스레드를 공식적으로 지원하고 관련 기능을 제공하기 시작한 핵심 버전은 스프링 부트 3.2 야 (2023년 11월 출시). 스프링 부트 3.2는 Java 21을 베이스라인으로 지원하며, 가상
> 스레드를 쉽게 활용할 수 있는 기능들을 도입했어.
> ```yaml
> spring:
>    threads:
>       virtual:
>           enabled: true
> ```
> 효과: 이 설정을 켜면, 톰캣은 더 이상 요청마다 플랫폼 스레드(OS 스레드)를 할당하는 스레드 풀을 사용하지 않고, 각 요청을 가상 스레드에서 실행해. 기존의 동기식 블로킹 I/O 코드(예: JDBC,
> RestTemplate, 외부 API 호출 등)를 그대로 사용하면서도, I/O 대기 시간에 플랫폼 스레드가 점유되지 않아 훨씬 적은 리소스로 높은 처리량을 달성할 수 있게 돼.

---

## 서버리스(Serverless) 대응

> * GraalVM 네이티브 이미지 지원 강화 (Spring Boot 3의 가장 큰 기여!)
> * Java 21의 가상 스레드 (Virtual Threads) (스프링 부트 3.2+ 에서 활용)
> * Spring Cloud Function 활용
> *

### Spring Cloud Function

> Spring Cloud Function은 AWS Lambda, Azure Functions, Google Cloud Functions 등 다양한 서버리스 플랫폼 위에서 스프링 기반 함수를 개발하기 위한 핵심
> 프로젝트야.
