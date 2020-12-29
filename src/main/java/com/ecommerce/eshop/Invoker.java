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
//        ProductCategory testCategory = new ProductCategory();
//        testCategory.setName("szpada");
//        categoryService.save(testCategory);

        Product testProduct = new Product();
        testProduct.setName("Szpada platikowa");
        testProduct.setDescription("Idealne dla dzieciaków");
        testProduct.setPrice(BigDecimal.valueOf(70.22));
        testProduct.setQuantity(40);
        testProduct.setCategory(categoryService.getOneByName("szpada"));

        productService.save(testProduct);





    }
}
