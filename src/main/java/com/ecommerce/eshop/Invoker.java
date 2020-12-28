package com.ecommerce.eshop;

import com.ecommerce.eshop.models.Product;
import com.ecommerce.eshop.models.ProductCategories;
import com.ecommerce.eshop.repositories.ProductRepository;
import com.ecommerce.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Invoker implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Product testProduct = new Product();
        testProduct.setName("Szabla");
        testProduct.setDescription("To bardzo dobra szabla, nieźle wyważona, sama leży w łapie");
        testProduct.setPrice(BigDecimal.valueOf(140.22));
        testProduct.setCategory(ProductCategories.WEAPONS);

        productRepository.save(testProduct);

        Product fromDB = productRepository.getOne(1L);
        fromDB.setQuantity(100);
        productService.update(fromDB);

    }
}
