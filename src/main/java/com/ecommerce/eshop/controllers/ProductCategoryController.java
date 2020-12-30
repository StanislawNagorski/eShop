package com.ecommerce.eshop.controllers;

import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.service.CategoryService;
import com.ecommerce.eshop.utils.excepctions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<String> getCategoriesNames(){
        return categoryService.getCategoryNames();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategory addProductCategory(@RequestParam String category){
        return categoryService.save(category);
    }

    //TODO PUT and DELETE for category => seperete to category controller!


    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError productNotFoundExceptionHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(CategoryCreationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiError creationExceptionHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }
}
