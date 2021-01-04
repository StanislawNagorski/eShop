package com.ecommerce.eshop.controllers;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.service.ProductService;
import com.ecommerce.eshop.utils.excepctions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.ecommerce.eshop.utils.ControllersUtils.ControllersUtils.ORDER_ASCENDING;
import static com.ecommerce.eshop.utils.ControllersUtils.ControllersUtils.ORDER_DESCENDING;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/promo")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllPromoProducts() {
        return productService.getAllPromoProducts();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
//   np http://localhost:8080/products/filter?category=szabla
    public List<Product> getAllByCategory(@RequestParam String category) {
        return productService.getAllByCategory(category);
    }

    @GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllByPrice(@RequestParam(required = false) String order,
                                       @RequestParam(required = false) String category) {

        if (category != null && order != null && order.equalsIgnoreCase(ORDER_ASCENDING)) {
            return productService.getAllByPriceAndCategoryAsc(category);
        }
        if (category != null && order != null && order.equalsIgnoreCase(ORDER_DESCENDING)) {
            return productService.getAllByPriceAndCategoryDesc(category);
        }

        if (order != null && order.equalsIgnoreCase(ORDER_ASCENDING)) {
            return productService.getAllProductsByPriceAsc();
        }
        if (order != null && order.equalsIgnoreCase(ORDER_DESCENDING)) {
            return productService.getAllProductsByPriceDesc();
        }

        return productService.getAllProductsByPriceAsc();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllByName(@RequestParam String name) {
        return productService.getAllByName(name);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product deleteProduct(@PathVariable Long id) {
        return productService.deactivate(id);
    }

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError productNotFoundExceptionHandler(RuntimeException runtimeException) {
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ProductCreationException.class, CategoryCreationException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiError creationExceptionHandler(RuntimeException runtimeException) {
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }

}

