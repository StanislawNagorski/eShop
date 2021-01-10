package com.ecommerce.eshop.utils.excepctions;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.exceptions.OrderNotFoundException;
import com.ecommerce.eshop.product.exceptions.CategoryCreationException;
import com.ecommerce.eshop.product.exceptions.CategoryNotFoundException;
import com.ecommerce.eshop.product.exceptions.ProductCreationException;
import com.ecommerce.eshop.product.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            ProductNotFoundException.class, CategoryNotFoundException.class,
            OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError entityNotFoundHandler(RuntimeException runtimeException) {
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({
            ProductCreationException.class, CategoryCreationException.class,
            OrderCreationException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiError entityCreationHandler(RuntimeException runtimeException) {
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }
}
