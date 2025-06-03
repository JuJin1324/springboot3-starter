package com.starter.springboot3.chapter3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/native")
public class NativeApplication {

    @GetMapping("/")
    public String hello() {
        return "Hello from Native Image!";
    }

    @GetMapping("/memory")
    public Map<String, Object> memory() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memory = new HashMap<>();
        memory.put("maxMemory", runtime.maxMemory() / 1024 / 1024 + " MB");
        memory.put("totalMemory", runtime.totalMemory() / 1024 / 1024 + " MB");
        memory.put("freeMemory", runtime.freeMemory() / 1024 / 1024 + " MB");
        memory.put("usedMemory", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " MB");
        return memory;
    }

    @GetMapping("/startup-time")
    public Map<String, Object> startupTime() {
        Map<String, Object> info = new HashMap<>();
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("javaVendor", System.getProperty("java.vendor"));
        info.put("osName", System.getProperty("os.name"));
        info.put("osArch", System.getProperty("os.arch"));
        info.put("currentTimeMillis", System.currentTimeMillis());
        
        // JVM 시작 시간 (네이티브 이미지에서는 프로세스 시작 시간)
        long uptime = java.lang.management.ManagementFactory.getRuntimeMXBean().getUptime();
        info.put("uptimeMillis", uptime);
        info.put("uptimeSeconds", uptime / 1000.0);
        
        return info;
    }

    @GetMapping("/gc-info")
    public Map<String, Object> gcInfo() {
        Map<String, Object> gcInfo = new HashMap<>();
        
        // GC 정보 (네이티브 이미지에서는 제한적)
        try {
            Runtime runtime = Runtime.getRuntime();
            gcInfo.put("availableProcessors", runtime.availableProcessors());
            gcInfo.put("maxMemoryMB", runtime.maxMemory() / 1024 / 1024);
            gcInfo.put("totalMemoryMB", runtime.totalMemory() / 1024 / 1024);
            gcInfo.put("freeMemoryMB", runtime.freeMemory() / 1024 / 1024);
            
            // 메모리 사용률 계산
            double memoryUsagePercent = ((double)(runtime.totalMemory() - runtime.freeMemory()) / runtime.maxMemory()) * 100;
            gcInfo.put("memoryUsagePercent", String.format("%.2f%%", memoryUsagePercent));
            
        } catch (Exception e) {
            gcInfo.put("error", "GC info not available in native image: " + e.getMessage());
        }
        
        return gcInfo;
    }

    @GetMapping("/native-features")
    public Map<String, Object> nativeFeatures() {
        Map<String, Object> features = new HashMap<>();
        
        // 네이티브 이미지 특징 확인
        String javaVmName = System.getProperty("java.vm.name", "Unknown");
        boolean isNativeImage = javaVmName.contains("Substrate VM") || javaVmName.contains("GraalVM");
        
        features.put("isNativeImage", isNativeImage);
        features.put("javaVmName", javaVmName);
        features.put("javaRuntimeName", System.getProperty("java.runtime.name", "Unknown"));
        
        // 네이티브 이미지에서 사용 가능한 기능들
        features.put("reflectionSupport", "Limited - requires configuration");
        features.put("dynamicClassLoading", "Not supported");
        features.put("jniSupport", "Limited - requires configuration");
        features.put("startupSpeed", "Very fast (~100ms)");
        features.put("memoryFootprint", "Low (~50MB)");
        
        return features;
    }

    @GetMapping("/performance-test")
    public Map<String, Object> performanceTest() {
        Map<String, Object> result = new HashMap<>();
        
        long startTime = System.nanoTime();
        
        // 간단한 성능 테스트
        int iterations = 1000000;
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += i;
        }
        
        long endTime = System.nanoTime();
        long executionTimeNanos = endTime - startTime;
        
        result.put("iterations", iterations);
        result.put("sum", sum);
        result.put("executionTimeNanos", executionTimeNanos);
        result.put("executionTimeMillis", executionTimeNanos / 1_000_000.0);
        result.put("operationsPerSecond", (iterations * 1_000_000_000L) / executionTimeNanos);
        
        return result;
    }
}
