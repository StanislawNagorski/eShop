package com.ecommerce.eshop.utils.excepctions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ApiError productNotFoundHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(ProductCreationException.class)
    public ApiError productCreationHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }
}
