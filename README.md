# SpringBoot3 Starter

## 기반 환경 및 필수 변경 사항 이해 (가장 먼저!)

### Java 17 필수

> 왜 Java 17이 최소 요구 버전이 되었는지, Java 17의 주요 특징(Records, Sealed Classes, 향상된 switch 등)이 스프링 부트 3 개발에 어떻게
> 영향을 줄 수 있는지 가볍게 살펴보기. (깊게 팔 필요는 없지만, 기본은 알아야 해!)

### Jakarta EE 9+ 전환 (`javax` -> `jakarta`)

> 이 변경이 왜 일어났는지 배경을 이해하고, 실제 코드에서 임포트문 변경이 어떤 식으로 이루어지는지 파악. (가장 직접적인 코드
> 변경 사항!)

### Spring Framework 6 기반

> 스프링 부트 3가 스프링 프레임워크 6 위에 구축되었다는 사실 인지. 스프링 프레임워크 6의 주요 변경점 (위 Java 17, Jakarta EE 포함)이 부트
> 3에 그대로 영향을 준다는 점 이해.

--- 

## 핵심 의존성 및 설정 변경 사항 파악

### 주요 의존성 버전 업그레이드 확인

> 스프링 부트 3에서 관리하는 주요 라이브러리(Tomcat 10.1, Hibernate 6.1, Jackson 2.14 등) 버전이 크게 상승했다는 점 인지. 각
> 라이브러리별 주요 변경점이나 마이그레이션 가이드가 있다면 가볍게 훑어보기 (특히 많이 사용하는 라이브러리 위주).

### Configuration Property 변경 및 마이그레이션

> `application.properties` 또는 `application.yml` 설정 키 이름 변경, 제거, 구조 변경 사항 확인.
> 스프링 부트 팀에서 제공하는 **`spring-boot-properties-migrator`** 의존성 사용법 학습. (실제 마이그레이션 시 매우 중요!)

---

## 웹 개발 관련 변경 사항 (Spring MVC/WebFlux)

### Spring MVC Trailing Slash Matching 기본값 변경

> URL 경로 끝에 `/`가 있을 때 매핑 방식이 어떻게 변경되었는지 이해. (기존 애플리케이션 동작에 영향을 줄 수
> 있음!)

### RFC 7807 Problem Details 기본 지원

> 표준 에러 응답 형식이 어떻게 적용되는지 확인. 기존 에러 처리 방식과 비교.

### `@HttpExchange` 선언적 HTTP 클라이언트

> `RestTemplate`, `WebClient` 외 새로운 HTTP 클라이언트 방식 학습.

---

## 운영 및 고급 기능

### Observability 강화 (Micrometer & OpenTelemetry)

> 스프링 부트 3가 어떻게 메트릭과 트레이싱을 통합 지원하는지, 관련 설정 및 활용법 학습.

### GraalVM 네이티브 이미지 지원 강화

> AOT 처리 및 네이티브 이미지 빌드 방법, 장단점, 제약사항 학습.

### Spring Boot Actuator 변경 사항

> Actuator 엔드포인트 경로, 응답 형식, 보안 설정 등 변경 가능성 확인. (운영 시 중요!)

---

## 데이터 접근 기술 변경 사항 (필요시)

### Spring Data JPA (Hibernate 6 관련)

> Jakarta Persistence 네임스페이스 변경 외에 Hibernate 6 버전업으로 인한 영향 (쿼리 동작 방식, 새로운 기능,
> 제거된 기능 등) 확인.

### 사용하는 다른 Spring Data 모듈

> MongoDB, Redis 등의 변경 사항 확인 (주로 의존성 버전 업그레이드 관련).

---

## 보안 관련 변경 사항 (Spring Security)

### Spring Security 6 기반

> 스프링 시큐리티 설정 방식 변경 가능성 확인 (특히 Lambda DSL 도입 이후 변화 등). 마이그레이션 가이드 참고 필수.

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
