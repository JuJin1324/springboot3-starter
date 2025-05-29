package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.api.controller.exception.InsufficientStockException;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler { // ResponseEntityExceptionHandler 상속

    // 커스텀 예외 처리: InsufficientStockException
    @ExceptionHandler(InsufficientStockException.class)
    public ProblemDetail handleInsufficientStockException(
            InsufficientStockException ex, WebRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Insufficient Stock");
        problemDetail.setType(
                URI.create("https://example.com/probs/insufficient-stock")); // 예시적인 타입 URI
        problemDetail.setInstance(
                URI.create(request.getDescription(false))); // 요청 URI를 instance로 사용

        // 확장 필드 추가
        problemDetail.setProperty("productId", ex.getProductId());
        problemDetail.setProperty("requestedQuantity", ex.getRequestedQuantity());
        problemDetail.setProperty("availableStock", ex.getAvailableStock());
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        
        return problemDetail; // ProblemDetail 객체 직접 반환 (스프링이 ResponseEntity로 감싸줌)
    }

    // Bean Validation 예외 처리 (MethodArgumentNotValidException)
    // ResponseEntityExceptionHandler의 handleMethodArgumentNotValid 메서드를 오버라이드하여 ProblemDetail 사용
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(status, "입력값 유효성 검사에 실패했습니다.");
        problemDetail.setTitle("Validation Failed");
        problemDetail.setType(URI.create("https://example.com/probs/validation-error"));
        problemDetail.setInstance(URI.create(request.getDescription(false)));

        // 어떤 필드가 유효성 검사에 실패했는지 상세 정보 추가
        Map<String, Object> invalidParams =
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField,
                                        fieldError -> {
                                            Map<String, String> errorDetails = new HashMap<>();
                                            errorDetails.put(
                                                    "rejectedValue",
                                                    String.valueOf(fieldError.getRejectedValue()));
                                            errorDetails.put(
                                                    "message", fieldError.getDefaultMessage());
                                            return errorDetails;
                                        }));
        problemDetail.setProperty("invalid-params", invalidParams);
        problemDetail.setProperty("errorCount", ex.getBindingResult().getErrorCount());

        return ResponseEntity.status(status).body(problemDetail);
    }

    // 기타 일반적인 예외 처리 (최후의 보루)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, WebRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://example.com/probs/internal-server-error"));
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        // 프로덕션 환경에서는 ex.getMessage()를 직접 노출하지 않는 것이 좋을 수 있음
        problemDetail.setProperty("errorMessage", ex.getMessage());
        return problemDetail;
    }
}
