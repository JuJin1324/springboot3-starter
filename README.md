# SpringBoot3 Starter

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
