package com.ecommerce.eshop.order.exceptions;

public class OrderCreationException extends RuntimeException{
    public OrderCreationException(String message) {
        super(message);
    }
}
