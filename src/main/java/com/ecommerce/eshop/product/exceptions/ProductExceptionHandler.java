package com.ecommerce.eshop.product.exceptions;

import com.ecommerce.eshop.utils.excepctions.ApiError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

//TODO check if you can put here all api handlers from controllers
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
