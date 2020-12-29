package com.ecommerce.eshop;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.service.CategoryService;
import com.ecommerce.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Invoker implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        Product testProduct = new Product();
//        testProduct.setName("Szabla Bojowa");
//        testProduct.setDescription("Przeznaczona do cięcią kapusty i imponowania gościom wisząc na ścianie");
//        testProduct.setPrice(BigDecimal.valueOf(170.22));
//        testProduct.setQuantity(40);
//        testProduct.setCategory(categoryService.getOneByName("szabla"));
//
//        productService.save(testProduct);
//
//        ProductCategory testCategory = new ProductCategory();
//        testCategory.setName("szabla");
//        categoryService.save(testCategory);

        Product fromDB = productService.getById(2L);
        fromDB.setPromo(true);
        fromDB.setPromoPrice(BigDecimal.valueOf(9.99));
        productService.update(fromDB);



    }
}
