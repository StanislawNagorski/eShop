package com.ecommerce.eshop.controllers;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.service.CategoryService;
import com.ecommerce.eshop.service.ProductService;
import com.ecommerce.eshop.utils.exepctions.ProductCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

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

    @GetMapping("/categories")
    public Set<String> getCategoriesNames(){
        return categoryService.getCategoryNames();
    }

    @GetMapping("/promo")
    public List<Product> getAllPromoProducts(){
        return productService.getAllPromoProducts();
    }

    @GetMapping("/price")
    public List<Product> getAllByPrice(@RequestParam(required = false) String order){
        if (order != null && order.equalsIgnoreCase("asc")){
            return productService.getAllProductsByPriceAsc();
        }
        if (order != null && order.equalsIgnoreCase("desc")){
            return productService.getAllProductsByPriceDesc();
        }
        return productService.getAllProductsByPriceAsc();
    }

    @GetMapping("/filter")
//   np http://localhost:8080/products/filter?category=szabla
    public List<Product> getAllByCategory(@RequestParam String category){
        return productService.getAllByCategory(category);
    }

    @GetMapping("/searchByName")
    public List<Product> getAllByName(@RequestParam String name){
        return productService.getAllByName(name);
    }



}
