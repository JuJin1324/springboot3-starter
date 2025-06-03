package com.starter.springboot3.chapter3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;

/**
 * 실무 중심의 네이티브 이미지 어노테이션 적용
 * 필요한 곳에만 최소한으로 어노테이션 사용
 */
@RestController
@RequestMapping("/problems")
@RegisterReflectionForBinding({
    // Jackson 직렬화가 필요한 DTO 클래스만 등록
    ProblematicController.TestObject.class
})
public class ProblematicController {

    private static final String ERROR_KEY = "error";
    private static final String ERROR_TYPE_KEY = "errorType";
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 리플렉션 테스트
     * 주의: 동적 클래스 로딩은 네이티브 이미지에서 제한적
     * 실무에서는 가능한 피하고, 필요시 명시적 등록 권장
     */
    @GetMapping("/reflection")
    public Map<String, Object> testReflection(@RequestParam(defaultValue = "java.util.ArrayList") String className) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 동적 클래스 로딩 - 가능한 피해야 할 패턴
            Class<?> clazz = Class.forName(className);
            result.put("className", clazz.getName());
            result.put("classLoaded", true);
            
            Constructor<?>[] constructors = clazz.getConstructors();
            result.put("constructorCount", constructors.length);
            
            Field[] fields = clazz.getDeclaredFields();
            result.put("fieldCount", fields.length);
            
            Method[] methods = clazz.getMethods();
            result.put("methodCount", methods.length);
            
            Object instance = clazz.getDeclaredConstructor().newInstance();
            result.put("instanceCreated", true);
            result.put("instanceType", instance.getClass().getSimpleName());
            
        } catch (Exception e) {
            result.put(ERROR_KEY, e.getMessage());
            result.put(ERROR_TYPE_KEY, e.getClass().getSimpleName());
            result.put("classLoaded", false);
        }
        
        return result;
    }

    /**
     * 리소스 접근 테스트
     * Spring Boot 3에서 기본 리소스는 자동 포함되므로 별도 설정 불필요
     */
    @GetMapping("/resource")
    public Map<String, Object> testResource(@RequestParam(defaultValue = "application.yml") String fileName) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            result.put("resourceExists", resource.exists());
            result.put("resourcePath", resource.getPath());
            
            if (resource.exists()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    result.put("contentLength", content.length());
                    result.put("contentPreview", content.substring(0, Math.min(100, content.length())));
                }
            }
            
        } catch (IOException e) {
            result.put(ERROR_KEY, e.getMessage());
            result.put(ERROR_TYPE_KEY, e.getClass().getSimpleName());
        }
        
        return result;
    }

    /**
     * JSON 직렬화 테스트
     * @RegisterReflectionForBinding이 클래스 레벨에 이미 적용되어 있어 별도 설정 불필요
     * 실무 팁: RestController의 반환 타입은 Spring이 자동으로 감지하지만,
     * 내부에서 사용하는 DTO는 명시적 등록 필요
     */
    @GetMapping("/serialization")
    public Map<String, Object> testSerialization() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            TestObject testObj = new TestObject();
            testObj.setName("실무 최적화된 네이티브 테스트");
            testObj.setValue(42);
            testObj.getMetadata().put("key1", "value1");
            testObj.getMetadata().put("key2", 123);
            testObj.getMetadata().put("approach", "minimal-annotations");
            
            // Jackson 직렬화 - TestObject는 클래스 레벨에서 이미 등록됨
            String jsonString = objectMapper.writeValueAsString(testObj);
            result.put("serialized", jsonString);
            result.put("serializedLength", jsonString.length());
            
            // 역직렬화
            TestObject deserializedObj = objectMapper.readValue(jsonString, TestObject.class);
            result.put("deserializedName", deserializedObj.getName());
            result.put("deserializedValue", deserializedObj.getValue());
            result.put("deserializedMetadataSize", deserializedObj.getMetadata().size());
            
            // JsonNode는 Spring Boot에서 기본 지원
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            result.put("dynamicName", jsonNode.get("name").asText());
            result.put("dynamicValue", jsonNode.get("value").asInt());
            
        } catch (Exception e) {
            result.put(ERROR_KEY, e.getMessage());
            result.put(ERROR_TYPE_KEY, e.getClass().getSimpleName());
        }
        
        return result;
    }

    /**
     * 동적 프록시 테스트
     * Spring Boot 3에서 기본 JDK 프록시는 자동 지원
     */
    @GetMapping("/proxy")
    public Map<String, Object> testProxy() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Class<?>[] interfaces = {java.util.Map.class};
            
            Object proxy = java.lang.reflect.Proxy.newProxyInstance(
                classLoader,
                interfaces,
                (proxyObj, method, args) -> {
                    if ("toString".equals(method.getName())) {
                        return "Optimized Dynamic Proxy";
                    }
                    return switch (method.getName()) {
                        case "hashCode" -> System.identityHashCode(proxyObj);
                        case "equals" -> proxyObj == args[0];
                        default -> null;
                    };
                }
            );
            
            result.put("proxyCreated", true);
            result.put("proxyString", proxy.toString());
            result.put("proxyClass", proxy.getClass().getName());
            
        } catch (Exception e) {
            result.put(ERROR_KEY, e.getMessage());
            result.put(ERROR_TYPE_KEY, e.getClass().getSimpleName());
            result.put("proxyCreated", false);
        }
        
        return result;
    }

    /**
     * 메서드 리플렉션 호출 테스트
     * String 클래스는 GraalVM에서 기본 지원하므로 별도 설정 불필요
     */
    @GetMapping("/method-invoke")
    public Map<String, Object> testMethodInvoke() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Class<?> stringClass = String.class;
            Method lengthMethod = stringClass.getMethod("length");
            Method upperCaseMethod = stringClass.getMethod("toUpperCase");
            
            String testString = "Hello Optimized Native Image";
            
            Integer length = (Integer) lengthMethod.invoke(testString);
            String upperCase = (String) upperCaseMethod.invoke(testString);
            
            result.put("originalString", testString);
            result.put("stringLength", length);
            result.put("upperCaseString", upperCase);
            result.put("methodCallSuccessful", true);
            
        } catch (Exception e) {
            result.put(ERROR_KEY, e.getMessage());
            result.put(ERROR_TYPE_KEY, e.getClass().getSimpleName());
            result.put("methodCallSuccessful", false);
        }
        
        return result;
    }

    /**
     * 테스트용 DTO 클래스
     * 
     * 실무 팁:
     * - 클래스 레벨 @RegisterReflectionForBinding으로 충분
     * - 각 멤버에 개별 어노테이션 불필요 (과도한 설정)
     * - Jackson이 필요한 모든 메타데이터를 자동 등록
     */
    public static class TestObject {
        private String name;
        private Integer value;
        private Map<String, Object> metadata = new HashMap<>();

        // 기본 생성자 - Jackson 역직렬화용
        public TestObject() {
        }

        // Getters and Setters - 별도 어노테이션 불필요
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getValue() { return value; }
        public void setValue(Integer value) { this.value = value; }
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }
}
