package com.ecommerce.eshop;

import com.ecommerce.eshop.product.service.CategoryService;
import com.ecommerce.eshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Invoker implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

    }
}
