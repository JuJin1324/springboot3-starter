package com.starter.springboot3.chapter2.config;

import com.starter.springboot3.chapter2.service.MyData;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.util.ClassUtils;

@Configuration
@ImportRuntimeHints(AppRuntimeHints.MyDataHints.class)
public class AppRuntimeHints {

    static class MyDataHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            // 애플리케이션에서 MyData 클래스를 사용하는지 확인 (선택적이지만 좋은 습관)
            if (!ClassUtils.isPresent("com.starter.springboot3.chapter2.service.MyData", classLoader)) {
                return;
            }

            // 1. 리플렉션 힌트 (Reflection Hints)
            // MyData 클래스의 모든 public 생성자, public 메서드, public 필드에 대한 리플렉션 접근을 허용
            // Jackson이 (역)직렬화 시 이 정보들을 사용함
            hints.reflection().registerType(
                    MyData.class,
                    hint -> hint.withMembers(
                            org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, // 선언된 생성자 호출
                            org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_METHODS,  // 선언된 메서드 호출
                            org.springframework.aot.hint.MemberCategory.DECLARED_FIELDS         // 선언된 필드 접근
                            // 더 세밀하게 특정 생성자, 메서드, 필드만 지정할 수도 있음
                            // 예: .onReachableType(MyData.class).methods(method -> method.getName().equals("getName"))
                    )
            );
            // 만약 특정 생성자만 필요하다면:
            // hints.reflection().registerConstructor(MyData.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
            // hints.reflection().registerConstructor(MyData.class.getDeclaredConstructor(String.class, int.class), ExecutableMode.INVOKE);

            // 2. 리소스 힌트 (Resource Hints)
            // 클래스패스에서 "config/my-data.json" 파일을 읽어올 수 있도록 힌트 제공
            hints.resources().registerPattern("config/my-data.json");
            // 여러 패턴을 등록하거나, 특정 디렉토리 전체를 포함할 수도 있음
            // hints.resources().registerPattern("static/*");

            // 3. 직렬화 힌트 (Serialization Hints) - 필요하다면
            // 만약 MyData 객체를 자바 직렬화한다면
            // hints.serialization().registerType(MyData.class);

            // 4. JDK 프록시 힌트 (JDK Proxy Hints) - 필요하다면
            // 만약 특정 인터페이스에 대해 동적 프록시를 생성한다면
            // hints.proxies().registerJdkProxy(MyServiceInterface.class);
        }
    }
}
