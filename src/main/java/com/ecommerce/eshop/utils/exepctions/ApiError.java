package com.ecommerce.eshop.utils.exepctions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ApiError {
    private UUID id;
    private String message;
    private LocalDateTime timestamp;
}
