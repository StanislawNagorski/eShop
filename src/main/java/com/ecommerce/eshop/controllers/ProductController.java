package com.ecommerce.eshop.controllers;

import com.ecommerce.eshop.models.Product;
import com.ecommerce.eshop.service.ProductService;
import com.ecommerce.eshop.utils.exepctions.ProductCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Product byId;
        try {
            byId = productService.getById(id);
        } catch (ProductCreationException e){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
        }
        return ResponseEntity.ok(byId);
    }

    //TODO: to nie działa
    @GetMapping("/filer")
    public List<Product> getAllByCategory(@RequestParam String category){
        return productService.getAllByCategory(category);
    }

    @GetMapping("/categories")
    public List<String> getCategoriesNames(){
        return Collections.emptyList();
    }

}
