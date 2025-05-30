package com.starter.springboot3.chapter2.service;

import io.micrometer.common.KeyValues; // KeyValues 사용
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed; // @Observed 어노테이션

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j // Lombok을 사용한 로깅
public class MyObservedService {

    private final ObservationRegistry observationRegistry;
    private final Random random = new Random();

    public MyObservedService(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    // 방법 1: @Observed 어노테이션 사용 (AOP 기반)
    // "my.service.doSomethingAOP"는 관찰의 이름(컨텍스트 이름)이 됩니다.
    // 이 이름은 메트릭 이름의 일부나 스팬 이름으로 사용될 수 있습니다.
    @Observed(
            name = "my.service.doSomethingAOP",
            contextualName = "do-something-annotated", // 스팬 이름 등에 사용될 수 있는 더 친숙한 이름
            lowCardinalityKeyValues = {"service.type", "business"}) // 낮은 카디널리티 태그
    public String doSomethingAOP(String input) throws InterruptedException {
        log.info("AOP - Input: {}", input);
        if ("error".equalsIgnoreCase(input)) {
            log.error("AOP - Simulating error for input: {}", input);
            throw new RuntimeException("Simulated AOP error");
        }
        TimeUnit.MILLISECONDS.sleep(random.nextInt(200) + 50); // 임의의 시간 지연
        String result = "AOP - Processed: " + input.toUpperCase();
        log.info("AOP - Result: {}", result);
        return result;
    }

    // 방법 2: 프로그래매틱 API 사용
    public String doSomethingProgrammatic(String userId, String itemId)
            throws InterruptedException {
        // Observation 생성 및 컨텍스트 설정
        Observation observation =
                Observation.createNotStarted(
                                "my.service.doSomethingProgrammatic", observationRegistry)
                        .contextualName("do-something-programmatic")
                        // 낮은 카디널리티 태그: 메트릭에 포함될 수 있음
                        .lowCardinalityKeyValue("item.type", getItemType(itemId))
                        // 높은 카디널리티 태그: 트레이스나 로그에는 포함되지만, 메트릭 태그로는 부적합 (너무 많은 시계열 생성)
                        .highCardinalityKeyValue("user.id", userId)
                        .highCardinalityKeyValue("item.id", itemId);

        // 관찰 시작
        observation.start();
        log.info("Programmatic - User ID: {}, Item ID: {}", userId, itemId);

        try {
            // 실제 작업 수행
            if ("item-error".equals(itemId)) {
                log.error("Programmatic - Simulating error for item: {}", itemId);
                throw new RuntimeException("Simulated programmatic error for item: " + itemId);
            }
            TimeUnit.MILLISECONDS.sleep(random.nextInt(300) + 100);
            String result = "Programmatic - Processed item " + itemId + " for user " + userId;
            log.info("Programmatic - Result: {}", result);

            // 관찰 이벤트 기록 (선택 사항)
            observation.event(
                    Observation.Event.of("item.processed", "Item successfully processed"));

            return result;
        } catch (Exception e) {
            // 에러 기록
            observation.error(e);
            throw e; // 예외 다시 던지기
        } finally {
            // 관찰 중지 (성공/실패 여부와 관계없이 항상 호출되도록 finally 블록에)
            observation.stop();
        }
    }

    // KeyValues를 사용하여 태그를 동적으로 추가하는 더 유연한 방법 (프로그래매틱 API)
//    public String doSomethingWithKeyValues(String customerSegment) throws InterruptedException {
//        return Observation.createNotStarted(
//                        "my.service.doSomethingWithKeyValues", observationRegistry)
//                .contextualName("do-something-keyvalues")
//                // observe() 메서드를 사용하면 start(), stop(), error()를 자동으로 처리
//                .observe(
//                        () -> {
//                            log.info("KeyValues - Customer Segment: {}", customerSegment);
//                            KeyValues keyValues =
//                                    KeyValues.of("customer.segment", customerSegment); // 동적 태그
//                            if (customerSegment.length() > 10) { // 예시: 높은 카디널리티가 될 수 있는 조건
//                                keyValues =
//                                        keyValues.and(
//                                                "segment.length",
//                                                String.valueOf(customerSegment.length()));
//                            }
//                            // 현재 관찰 컨텍스트에 태그 추가 (observe() 내부에서)
//                            Observation.Context currentContext =
//                                    observationRegistry
//                                            .getCurrentObservationScope()
//                                            .getCurrentObservation()
//                                            .getContext();
//                            currentContext.put(keyValues);
//
//                            if ("error-segment".equalsIgnoreCase(customerSegment)) {
//                                log.error(
//                                        "KeyValues - Simulating error for segment: {}",
//                                        customerSegment);
//                                throw new RuntimeException("Simulated KeyValues error");
//                            }
//                            try {
//                                TimeUnit.MILLISECONDS.sleep(random.nextInt(150) + 80);
//                            } catch (InterruptedException e) {
//                                Thread.currentThread().interrupt();
//                                throw new RuntimeException(e);
//                            }
//                            String result = "KeyValues - Processed segment: " + customerSegment;
//                            log.info("KeyValues - Result: {}", result);
//                            return result;
//                        });
//    }

    private String getItemType(String itemId) {
        if (itemId != null && itemId.startsWith("BOOK")) {
            return "book";
        }
        return "generic";
    }
}
